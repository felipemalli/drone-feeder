package io.github.nataelienai.dronefeeder.delivery;

import io.github.nataelienai.dronefeeder.delivery.exception.NotFoundException;
import io.github.nataelienai.dronefeeder.drone.Drone;
import io.github.nataelienai.dronefeeder.drone.DroneRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for handling business logic of deliveries.
 */
@Service
public class DeliveryService {

  @Autowired
  private DeliveryRepository deliveryRepository;

  @Autowired
  private DroneRepository droneRepository;

  /**
   * Saves a delivery entity.
   *
   * @param delivery the delivery to save.
   * @return the saved delivery.
   */
  public Delivery create(Delivery delivery) {
    return deliveryRepository.save(delivery);
  }

  /**
   * Retrieves all deliveries.
   *
   * @return all deliveries.
   */
  public List<Delivery> findAll() {
    return deliveryRepository.findAll();
  }

  /**
   * Updates the status of a delivery.
   *
   * @param id the id of the delivery to update.
   * @param status the new status value.
   * @return the updated delivery.
   * @throws NotFoundException if a delivery with {@literal id} does not exist.
   */
  public Delivery updateStatus(Long id, Status status) {
    Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);
    if (optionalDelivery.isEmpty()) {
      throw new NotFoundException("Delivery not found.");
    }
    Delivery delivery = optionalDelivery.get();
    
    delivery.setStatus(status);
    deliveryRepository.save(delivery);
    return delivery;
  }

  /**
   * Updates the drone of a delivery.
   *
   * @param id the id of the delivery to update.
   * @param droneId the id of the new drone.
   * @return the updated delivery.
   * @throws NotFoundException if a delivery with {@literal id} or a drone with
   *     {@literal droneId} does not exist.
   */
  public Delivery updateDrone(Long id, Long droneId) {
    Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);
    if (optionalDelivery.isEmpty()) {
      throw new NotFoundException("Delivery not found.");
    }
    Delivery delivery = optionalDelivery.get();

    Optional<Drone> optionalDrone = droneRepository.findById(id);
    if (optionalDrone.isEmpty()) {
      throw new NotFoundException("Drone not found.");
    }
    Drone drone = optionalDrone.get();
  
    delivery.setDrone(drone);
    deliveryRepository.save(delivery);
    return delivery;
  }

  /**
   * Deletes the delivery by its id.
   *
   * @param id the id of the delivery to delete.
   * @throws NotFoundException if a delivery with {@literal id} does not exist.
   */
  public void delete(Long id) {
    boolean isDeliveryExist = deliveryRepository.existsById(id);
    if (!isDeliveryExist) {
      throw new NotFoundException("Delivery not found.");
    }
    deliveryRepository.deleteById(id);
  }

}
