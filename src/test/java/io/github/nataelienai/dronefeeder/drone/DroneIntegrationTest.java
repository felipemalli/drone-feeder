package io.github.nataelienai.dronefeeder.drone;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

    assertEquals(droneRepository.findAll().size(), 1);
  }

}
