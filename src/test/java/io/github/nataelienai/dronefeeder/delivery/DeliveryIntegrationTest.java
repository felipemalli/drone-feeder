package io.github.nataelienai.dronefeeder.delivery;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.matchesRegex;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
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
      .andExpect(jsonPath("$.drone").value(nullValue()));

    List<Delivery> deliveries = deliveryRepository.findAll();
    assertEquals(deliveries.size(), 1);

    Delivery delivery = deliveries.get(0);
    assertEquals("READY", delivery.getStatus().name());
    assertNotNull(delivery.getStatusLastModified());
    assertNull(delivery.getDrone());
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
      .andExpect(jsonPath("$[0].drone").value(nullValue()));
  }
}
