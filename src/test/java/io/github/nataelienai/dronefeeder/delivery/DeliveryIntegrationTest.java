package io.github.nataelienai.dronefeeder.delivery;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.matchesRegex;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import io.github.nataelienai.dronefeeder.drone.Drone;
import io.github.nataelienai.dronefeeder.drone.DroneRepository;

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DeliveryRepository deliveryRepository;

  @Autowired
  private DroneRepository droneRepository;

  @AfterEach
  void cleanUp() {
    deliveryRepository.deleteAll();
    droneRepository.deleteAll();
  }

  @Test
  @DisplayName("Create delivery request should return the created delivery and status code 201 when given a valid delivery")
  void createDelivery_shouldReturnCreatedDeliveryAndStatusCode201_givenValidDelivery() throws Exception {
    MockHttpServletRequestBuilder createDeliveryRequest = post("/delivery")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .content("{ \"status\": \"READY\" }");

    mockMvc.perform(createDeliveryRequest)
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(greaterThan(0)))
      .andExpect(jsonPath("$.status").value("READY"))
      .andExpect(jsonPath("$.statusLastModified").value(matchesRegex("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{1,}Z)")))
      .andExpect(jsonPath("$.drone").value(nullValue()))
      .andExpect(jsonPath("$.videoId").value(nullValue()));

    List<Delivery> deliveries = deliveryRepository.findAll();
    assertEquals(deliveries.size(), 1);

    Delivery delivery = deliveries.get(0);
    assertEquals("READY", delivery.getStatus().name());
    assertNotNull(delivery.getStatusLastModified());
    assertNull(delivery.getDrone());
    assertNull(delivery.getVideoId());
  }

  @Test
  @DisplayName("Find all deliveries request should return the all deliveries and status code 200")
  void findAllDeliveries_shouldReturnAllDeliveriesAndStatusCode200() throws Exception {
    Delivery delivery = new Delivery();
    delivery.setStatus(Status.READY);
    delivery.setStatusLastModified(Instant.now());
    Delivery savedDelivery = deliveryRepository.save(delivery);

    mockMvc.perform(get("/delivery"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$[0].id").value(savedDelivery.getId()))
      .andExpect(jsonPath("$[0].status").value(savedDelivery.getStatus().name()))
      .andExpect(jsonPath("$[0].statusLastModified").value(savedDelivery.getStatusLastModified().toString()))
      .andExpect(jsonPath("$[0].drone").value(nullValue()))
      .andExpect(jsonPath("$[0].videoId").value(nullValue()));
  }

  @Test
  @DisplayName("Find delivery by id request should return a delivery and status code 200 when given a valid id")
  void findDeliveryById_shouldReturnDeliveryAndStatusCode200_givenValidId() throws Exception {
    Delivery delivery = new Delivery();
    delivery.setStatus(Status.READY);
    delivery.setStatusLastModified(Instant.now());
    Delivery savedDelivery = deliveryRepository.save(delivery);

    mockMvc.perform(get("/delivery/" + savedDelivery.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(savedDelivery.getId()))
      .andExpect(jsonPath("$.status").value(savedDelivery.getStatus().name()))
      .andExpect(jsonPath("$.statusLastModified").value(savedDelivery.getStatusLastModified().toString()))
      .andExpect(jsonPath("$.drone").value(nullValue()))
      .andExpect(jsonPath("$.videoId").value(nullValue()));
  }

  @Test
  @DisplayName("Find delivery by id request should return a message and status code 404 when given an invalid id")
  void findDeliveryById_shouldReturnMessageAndStatusCode404_givenInvalidId() throws Exception {
    mockMvc.perform(get("/delivery/404"))
      .andExpect(status().isNotFound())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value(containsString("Delivery not found")));
  }

  @Test
  @DisplayName("Update delivery request should return the updated delivery and status code 200 when given a valid id and delivery")
  void updateDelivery_shouldReturnUpdatedDeliveryAndStatusCode200_givenValidIdAndDelivery() throws Exception {
    Delivery delivery = new Delivery();
    delivery.setStatus(Status.READY);
    delivery.setStatusLastModified(Instant.now());
    Delivery savedDelivery = deliveryRepository.save(delivery);

    MockHttpServletRequestBuilder updateDeliveryRequest = put("/delivery/" + savedDelivery.getId())
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .content("{ \"status\": \"SHIPPED\", \"statusLastModified\": \"2022-10-19T17:20:00Z\" }");

    mockMvc.perform(updateDeliveryRequest)
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(savedDelivery.getId()))
      .andExpect(jsonPath("$.status").value("SHIPPED"))
      .andExpect(jsonPath("$.statusLastModified").value("2022-10-19T17:20:00Z"))
      .andExpect(jsonPath("$.drone").value(nullValue()))
      .andExpect(jsonPath("$.videoId").value(nullValue()));

    Optional<Delivery> optionalDelivery = deliveryRepository.findById(savedDelivery.getId());
    Delivery updatedDelivery = optionalDelivery.get();
    assertEquals("SHIPPED", updatedDelivery.getStatus().name());
    assertEquals("2022-10-19T17:20:00Z", updatedDelivery.getStatusLastModified().toString());
    assertNull(updatedDelivery.getDrone());
    assertNull(updatedDelivery.getVideoId());
  }

  @Test
  @DisplayName("Update delivery request should return a message and status code 404 when given an invalid id")
  void updateDelivery_shouldReturnMessageAndStatusCode404_givenInvalidId() throws Exception {
    MockHttpServletRequestBuilder updateDeliveryRequest = put("/delivery/200")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .content("{ \"status\": \"SHIPPED\", \"statusLastModified\": \"2022-10-19T17:20:00Z\" }");

    mockMvc.perform(updateDeliveryRequest)
      .andExpect(status().isNotFound())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value(containsString("Delivery not found")));
  }

  @Test
  @DisplayName("Update delivery drone request should return the updated delivery and status code 200 when given valid delivery and drone ids")
  void updateDeliveryDrone_shouldReturnUpdatedDeliveryAndStatusCode200_givenValidDeliveryAndDroneIds() throws Exception {
    Delivery delivery = new Delivery();
    delivery.setStatus(Status.READY);
    delivery.setStatusLastModified(Instant.now());
    Delivery savedDelivery = deliveryRepository.save(delivery);

    Drone drone = new Drone();
    drone.setLatitude("13.404954");
    drone.setLongitude("52.520008");
    Drone savedDrone = droneRepository.save(drone);

    MockHttpServletRequestBuilder updateDeliveryDroneRequest = patch("/delivery/"
        + savedDelivery.getId() + "/drone/" + savedDrone.getId());

    mockMvc.perform(updateDeliveryDroneRequest)
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(savedDelivery.getId()))
      .andExpect(jsonPath("$.status").value(savedDelivery.getStatus().name()))
      .andExpect(jsonPath("$.statusLastModified").value(savedDelivery.getStatusLastModified().toString()))
      .andExpect(jsonPath("$.drone").value(notNullValue()))
      .andExpect(jsonPath("$.drone.id").value(savedDrone.getId()))
      .andExpect(jsonPath("$.drone.latitude").value(savedDrone.getLatitude()))
      .andExpect(jsonPath("$.drone.longitude").value(savedDrone.getLongitude()))
      .andExpect(jsonPath("$.videoId").value(nullValue()));

    Optional<Delivery> optionalDelivery = deliveryRepository.findById(savedDelivery.getId());
    Delivery updatedDelivery = optionalDelivery.get();
    Drone deliveryDrone = updatedDelivery.getDrone();

    assertEquals(savedDrone.getId(), deliveryDrone.getId());
    assertEquals(savedDrone.getLatitude(), deliveryDrone.getLatitude());
    assertEquals(savedDrone.getLongitude(), deliveryDrone.getLongitude().toString());
  }

  @Test
  @DisplayName("Update delivery drone request should return a message and status code 404 when given an invalid delivery id")
  void updateDeliveryDrone_shouldReturnMessageAndStatusCode404_givenInvalidDeliveryId() throws Exception {
    Drone drone = new Drone();
    drone.setLatitude("13.404954");
    drone.setLongitude("52.520008");
    Drone savedDrone = droneRepository.save(drone);

    MockHttpServletRequestBuilder updateDeliveryDroneRequest = patch("/delivery/20/drone/" + savedDrone.getId());

    mockMvc.perform(updateDeliveryDroneRequest)
      .andExpect(status().isNotFound())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value(containsString("Delivery not found")));
  }

  @Test
  @DisplayName("Update delivery drone request should return a message and status code 404 when given an invalid drone id")
  void updateDeliveryDrone_shouldReturnMessageAndStatusCode404_givenInvalidDroneId() throws Exception {
    Delivery delivery = new Delivery();
    delivery.setStatus(Status.READY);
    delivery.setStatusLastModified(Instant.now());
    Delivery savedDelivery = deliveryRepository.save(delivery);

    MockHttpServletRequestBuilder updateDeliveryDroneRequest = patch("/delivery/"
        + savedDelivery.getId() + "/drone/20");

    mockMvc.perform(updateDeliveryDroneRequest)
      .andExpect(status().isNotFound())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value(containsString("Drone not found")));
  }

  @Test
  @DisplayName("Delete delivery request should return status code 204 when given a valid id")
  void deleteDelivery_shouldReturnStatusCode204_givenValidId() throws Exception {
    Delivery delivery = new Delivery();
    delivery.setStatus(Status.READY);
    delivery.setStatusLastModified(Instant.now());
    Delivery savedDelivery = deliveryRepository.save(delivery);

    mockMvc.perform(delete("/delivery/" + savedDelivery.getId()))
      .andExpect(status().isNoContent());

    Optional<Delivery> optionalDelivery = deliveryRepository.findById(savedDelivery.getId());
    assertTrue(optionalDelivery.isEmpty());
  }

  @Test
  @DisplayName("Delete delivery request should return a message and status code 404 when given an invalid id")
  void deleteDelivery_shouldReturnMessageAndStatusCode404_givenInvalidId() throws Exception {
    mockMvc.perform(delete("/delivery/500"))
      .andExpect(status().isNotFound())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value(containsString("Delivery not found")));
  }

}
