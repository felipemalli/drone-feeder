package io.github.nataelienai.dronefeeder.delivery;

import io.github.nataelienai.dronefeeder.delivery.exception.DeliveryNotFoundException;
import io.github.nataelienai.dronefeeder.drone.Drone;
import io.github.nataelienai.dronefeeder.drone.DroneRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import io.github.nataelienai.dronefeeder.drone.exception.DroneNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

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
  @Transactional
  public Delivery create(Delivery delivery) {
    delivery.setStatusLastModified(Instant.now());
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
   * @param updatedDelivery the new delivery.
   * @return the updated delivery.
   * @throws DeliveryNotFoundException if a delivery with {@literal id} does not exist.
   */
  @Transactional
  public Delivery update(Long id, Delivery updatedDelivery) {
    Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);
    if (optionalDelivery.isEmpty()) {
      throw new DeliveryNotFoundException("Delivery not found.");
    }
    Delivery delivery = optionalDelivery.get();
    delivery.setStatus(updatedDelivery.getStatus());
    delivery.setStatusLastModified(updatedDelivery.getStatusLastModified());
    return delivery;
  }

  /**
   * Updates the drone of a delivery.
   *
   * @param id the id of the delivery to update.
   * @param droneId the id of the new drone.
   * @return the updated delivery.
   * @throws DeliveryNotFoundException if a delivery with {@literal id} or a drone with
   *     {@literal droneId} does not exist.
   */
  @Transactional
  public Delivery updateDrone(Long id, Long droneId) {
    Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);
    if (optionalDelivery.isEmpty()) {
      throw new DeliveryNotFoundException("Delivery not found.");
    }
    Delivery delivery = optionalDelivery.get();
    Optional<Drone> optionalDrone = droneRepository.findById(droneId);
    if (optionalDrone.isEmpty()) {
      throw new DroneNotFoundException("Drone not found.");
    }
    Drone drone = optionalDrone.get();
    delivery.setDrone(drone);
    return delivery;
  }

  /**
   * Deletes the delivery by its id.
   *
   * @param id the id of the delivery to delete.
   * @throws DeliveryNotFoundException if a delivery with {@literal id} does not exist.
   */
  @Transactional
  public void delete(Long id) {
    boolean isDeliveryExist = deliveryRepository.existsById(id);
    if (!isDeliveryExist) {
      throw new DeliveryNotFoundException("Delivery not found.");
    }
    deliveryRepository.deleteById(id);
  }

}
