package io.github.nataelienai.dronefeeder.drone;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drone")
public class DroneController {

  @Autowired
  private DroneService droneService;

  @PostMapping
  public Drone create(@RequestBody Drone drone) {
    return droneService.create(drone);
  }

  @GetMapping
  public List<Drone> findAll() {
    return droneService.findAll();
  }

}
