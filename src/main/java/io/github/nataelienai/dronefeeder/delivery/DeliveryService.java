package io.github.nataelienai.dronefeeder.delivery;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nataelienai.dronefeeder.delivery.exception.NotFoundException;

@Service
public class DeliveryService {

  @Autowired
  private DeliveryRepository deliveryRepository;
  
  public Delivery create(Delivery delivery) {
    return deliveryRepository.save(delivery);
  }

  public List<Delivery> findAll() {
    return deliveryRepository.findAll();
  }

  public void updateStatus(Long id, Status status) {
    Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);
    if (optionalDelivery.isEmpty()) {
      throw new NotFoundException("Delivery not found.");
    }
    Delivery delivery = optionalDelivery.get();
    
    delivery.setStatus(status);
    deliveryRepository.save(delivery);
  }

  public void delete(Long id) {
    boolean isDeliveryExist = deliveryRepository.existsById(id);
    if (!isDeliveryExist) {
      throw new NotFoundException("Delivery not found.");
    }
    deliveryRepository.deleteById(id);
  }

}
