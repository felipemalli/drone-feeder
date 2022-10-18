package io.github.nataelienai.dronefeeder.drone;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneService {

  @Autowired
  private DroneRepository droneRepository;

  public Drone create(Drone drone) {
    return droneRepository.save(drone);
  }

  public List<Drone> findAll() {
    return droneRepository.findAll();
  }

}
