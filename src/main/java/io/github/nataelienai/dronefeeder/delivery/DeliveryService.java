package io.github.nataelienai.dronefeeder.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
