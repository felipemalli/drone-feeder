package io.github.felipemalli.dronefeeder.delivery;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling delivery resource requests.
 */
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

  private final DeliveryService deliveryService;

  @Autowired
  public DeliveryController(DeliveryService deliveryService) {
    this.deliveryService = deliveryService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Delivery create(@RequestBody Delivery delivery) {
    return deliveryService.create(delivery);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Delivery> findAll() {
    return deliveryService.findAll();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Delivery findById(@PathVariable Long id) {
    return deliveryService.findById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Delivery update(@PathVariable Long id, @RequestBody Delivery delivery) {
    return deliveryService.update(id, delivery);
  }

  @PatchMapping("/{id}/drone/{droneId}")
  @ResponseStatus(HttpStatus.OK)
  public Delivery updateDrone(@PathVariable Long id, @PathVariable Long droneId) {
    return deliveryService.updateDrone(id, droneId);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    deliveryService.delete(id);
  }

}
