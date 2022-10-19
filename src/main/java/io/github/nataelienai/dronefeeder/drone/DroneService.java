package io.github.nataelienai.dronefeeder.drone;

import io.github.nataelienai.dronefeeder.drone.exception.DroneNotFoundException;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for handling business logic of drones.
 */
@Service
public class DroneService {

  private final DroneRepository droneRepository;

  @Autowired
  public DroneService(DroneRepository droneRepository) {
    this.droneRepository = droneRepository;
  }

  /**
   * Saves a drone entity.
   *
   * @param drone the drone to save.
   * @return the saved drone.
   */
  @Transactional
  public Drone create(Drone drone) {
    return droneRepository.save(drone);
  }

  /**
   * Retrieves all drones.
   *
   * @return all drones.
   */
  public List<Drone> findAll() {
    return droneRepository.findAll();
  }

  /**
   * Retrieves a drone by its id.
   *
   * @param id the id of the drone.
   * @return the drone with the given id.
   * @throws DroneNotFoundException if a drone with {@literal id} does not exist.
   */
  public Drone findById(Long id) {
    Optional<Drone> optionalDrone = droneRepository.findById(id);
    if (optionalDrone.isEmpty()) {
      throw new DroneNotFoundException("Drone not found.");
    }
    return optionalDrone.get();
  }

  /**
   * Updates a drone by its id.
   *
   * @param id the id of the drone to update.
   * @param updatedDrone the new drone.
   * @return the updated drone.
   * @throws DroneNotFoundException if a drone with {@literal id} does not exist.
   */
  @Transactional
  public Drone update(Long id, Drone updatedDrone) {
    Optional<Drone> optionalDrone = droneRepository.findById(id);
    if (optionalDrone.isEmpty()) {
      throw new DroneNotFoundException("Drone not found.");
    }
    Drone drone = optionalDrone.get();
    drone.setLatitude(updatedDrone.getLatitude());
    drone.setLongitude(updatedDrone.getLongitude());
    return drone;
  }

  /**
   * Deletes a drone by its id.
   *
   * @param id the id of the drone to delete.
   * @throws DroneNotFoundException if a drone with {@literal id} does not exist.
   */
  @Transactional
  public void delete(Long id) {
    boolean droneExists = droneRepository.existsById(id);
    if (!droneExists) {
      throw new DroneNotFoundException("Drone not found.");
    }
    droneRepository.deleteById(id);
  }
}
