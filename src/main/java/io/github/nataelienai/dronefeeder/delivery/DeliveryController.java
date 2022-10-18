package io.github.nataelienai.dronefeeder.delivery;

import java.util.List;

import io.github.nataelienai.dronefeeder.drone.Drone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling delivery resource requests.
 */
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

  @Autowired
  private DeliveryService deliveryService;

  @PostMapping
  public Delivery create(@RequestBody Delivery delivery) {
    return deliveryService.create(delivery);
  }

  @GetMapping
  public List<Delivery> findAll() {
    return deliveryService.findAll();
  }

  @GetMapping("/{id}")
  public Delivery findById(@PathVariable Long id) {
    return deliveryService.findById(id);
  }

  @PutMapping("/{id}")
  public Drone update(@PathVariable Long id, @RequestBody Delivery delivery) {
    return deliveryService.update(id, delivery);
  }

  @PutMapping("/{id}")
  public Delivery updateStatus(@PathVariable Long id, @RequestBody Status status) {
    return deliveryService.updateStatus(id, status);
  }

  @PutMapping("/{id}/{droneId}")
  public Delivery updateDrone(@PathVariable Long id, @PathVariable Long droneId) {
    return deliveryService.updateDrone(id, droneId);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    deliveryService.delete(id);
  }

}
