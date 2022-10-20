package io.github.nataelienai.dronefeeder.drone;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.Optional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class DroneIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DroneRepository droneRepository;

  @AfterEach
  void cleanUp() {
    droneRepository.deleteAll();
  }

  @Test
  @DisplayName("Create drone request should return the created drone and status code 201 when given a valid drone")
  void createDrone_shouldReturnCreatedDroneAndStatusCode201_givenValidDrone() throws Exception {
    MockHttpServletRequestBuilder createDroneRequest = post("/drone")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .content("{ \"latitude\": \"-23.5489\", \"longitude\": \"-46.6388\" }");

    mockMvc.perform(createDroneRequest)
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(greaterThan(0)))
      .andExpect(jsonPath("$.latitude").value("-23.5489"))
      .andExpect(jsonPath("$.longitude").value("-46.6388"));

    List<Drone> drones = droneRepository.findAll();
    assertEquals(drones.size(), 1);

    Drone drone = drones.get(0);
    assertEquals("-23.5489", drone.getLatitude());
    assertEquals("-46.6388", drone.getLongitude());
  }

  @Test
  @DisplayName("Find all drones request should return the all drones and status code 200")
  void findAllDrones_shouldReturnAllDronesAndStatusCode200() throws Exception {
    Drone drone = new Drone();
    drone.setLatitude("-23.5489");
    drone.setLongitude("-46.6388");
    Drone savedDrone = droneRepository.save(drone);

    mockMvc.perform(get("/drone"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$[0].id").value(savedDrone.getId()))
      .andExpect(jsonPath("$[0].latitude").value(savedDrone.getLatitude()))
      .andExpect(jsonPath("$[0].longitude").value(savedDrone.getLongitude()));
  }

  @Test
  @DisplayName("Find drone by id request should return a drone and status code 200 when given a valid id")
  void findDroneById_shouldReturnDroneAndStatusCode200_givenValidId() throws Exception {
    Drone drone = new Drone();
    drone.setLatitude("13.404954");
    drone.setLongitude("52.520008");
    Drone savedDrone = droneRepository.save(drone);

    mockMvc.perform(get("/drone/" + savedDrone.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(savedDrone.getId()))
      .andExpect(jsonPath("$.latitude").value(savedDrone.getLatitude()))
      .andExpect(jsonPath("$.longitude").value(savedDrone.getLongitude()));
  }

  @Test
  @DisplayName("Find drone by id request should return a message and status code 404 when given an invalid id")
  void findDroneById_shouldReturnMessageAndStatusCode404_givenInvalidId() throws Exception {
    mockMvc.perform(get("/drone/101"))
      .andExpect(status().isNotFound())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value(containsString("Drone not found")));
  }

  @Test
  @DisplayName("Update drone request should return the updated drone and status code 200 when given a valid id and drone")
  void updateDrone_shouldReturnUpdatedDroneAndStatusCode200_givenValidIdAndDrone() throws Exception {
    Drone drone = new Drone();
    drone.setLatitude("13.404954");
    drone.setLongitude("52.520008");
    Drone savedDrone = droneRepository.save(drone);

    MockHttpServletRequestBuilder updateDroneRequest = put("/drone/" + savedDrone.getId())
    .accept(MediaType.APPLICATION_JSON)
    .contentType(MediaType.APPLICATION_JSON)
    .content("{ \"latitude\": \"-23.5489\", \"longitude\": \"-46.6388\" }");

    mockMvc.perform(updateDroneRequest)
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(savedDrone.getId()))
      .andExpect(jsonPath("$.latitude").value("-23.5489"))
      .andExpect(jsonPath("$.longitude").value("-46.6388"));

    Optional<Drone> optionalDrone = droneRepository.findById(savedDrone.getId());
    Drone updatedDrone = optionalDrone.get();
    assertEquals("-23.5489", updatedDrone.getLatitude());
    assertEquals("-46.6388", updatedDrone.getLongitude());
  }

  @Test
  @DisplayName("Update drone request should return a message and status code 404 when given an invalid id")
  void updateDrone_shouldReturnMessageAndStatusCode404_givenInvalidId() throws Exception {
    MockHttpServletRequestBuilder updateDroneRequest = put("/drone/50")
    .accept(MediaType.APPLICATION_JSON)
    .contentType(MediaType.APPLICATION_JSON)
    .content("{ \"latitude\": \"-23.5489\", \"longitude\": \"-46.6388\" }");

    mockMvc.perform(updateDroneRequest)
      .andExpect(status().isNotFound())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value(containsString("Drone not found")));
  }

  @Test
  @DisplayName("Delete drone request should return status code 204 when given a valid id")
  void deleteDrone_shouldReturnStatusCode204_givenValidId() throws Exception {
    Drone drone = new Drone();
    drone.setLatitude("13.404954");
    drone.setLongitude("52.520008");
    Drone savedDrone = droneRepository.save(drone);

    mockMvc.perform(delete("/drone/" + savedDrone.getId()))
      .andExpect(status().isNoContent());

    Optional<Drone> optionalDrone = droneRepository.findById(savedDrone.getId());
    assertTrue(optionalDrone.isEmpty());
  }

  @Test
  @DisplayName("Delete drone request should return a messge and status code 404 when given an invalid id")
  void deleteDrone_shouldReturnMessageAndStatusCode404_givenInvalidId() throws Exception {
    mockMvc.perform(delete("/drone/24"))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message").value(containsString("Drone not found")));
  }
}
