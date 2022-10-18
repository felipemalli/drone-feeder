package io.github.nataelienai.dronefeeder.delivery;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nataelienai.dronefeeder.delivery.exception.NotFoundException;
import io.github.nataelienai.dronefeeder.drone.Drone;
import io.github.nataelienai.dronefeeder.drone.DroneRepository;

@Service
public class DeliveryService {

  @Autowired
  private DeliveryRepository deliveryRepository;

  @Autowired
  private DroneRepository droneRepository;
  
  public Delivery create(Delivery delivery) {
    return deliveryRepository.save(delivery);
  }

  public List<Delivery> findAll() {
    return deliveryRepository.findAll();
  }

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

  public void delete(Long id) {
    boolean isDeliveryExist = deliveryRepository.existsById(id);
    if (!isDeliveryExist) {
      throw new NotFoundException("Delivery not found.");
    }
    deliveryRepository.deleteById(id);
  }

}
