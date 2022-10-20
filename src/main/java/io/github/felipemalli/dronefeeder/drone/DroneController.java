package io.github.felipemalli.dronefeeder.drone;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling drone resource requests.
 */
@RestController
@RequestMapping("/drone")
public class DroneController {

  private final DroneService droneService;

  @Autowired
  public DroneController(DroneService droneService) {
    this.droneService = droneService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Drone create(@RequestBody Drone drone) {
    return droneService.create(drone);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Drone> findAll() {
    return droneService.findAll();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Drone findById(@PathVariable Long id) {
    return droneService.findById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Drone update(@PathVariable Long id, @RequestBody Drone drone) {
    return droneService.update(id, drone);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    droneService.delete(id);
  }

}
