package io.github.nataelienai.dronefeeder.drone;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.nataelienai.dronefeeder.drone.exception.DroneNotFoundException;

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

  public Drone findById(Long id) {
    Optional<Drone> optionalDrone = droneRepository.findById(id);
    if (optionalDrone.isEmpty()) {
      throw new DroneNotFoundException("Drone not found.");
    }
    return optionalDrone.get();
  }
}
