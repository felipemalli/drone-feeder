package io.github.felipemalli.dronefeeder.drone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for handling drone entity storage.
 */
@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
}
