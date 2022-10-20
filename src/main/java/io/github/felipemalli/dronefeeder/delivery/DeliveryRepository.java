package io.github.felipemalli.dronefeeder.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for handling delivery entity storage.
 */
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
