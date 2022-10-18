package io.github.nataelienai.dronefeeder.delivery;

import java.util.List;

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

  public void delete(Long id) {
    boolean isDeliveryExist = deliveryRepository.existsById(id);
    if (!isDeliveryExist) {
      throw new NotFoundException("Delivery not found.");
    }
    deliveryRepository.deleteById(id);
  }

}
