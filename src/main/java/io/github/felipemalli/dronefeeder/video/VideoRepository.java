package io.github.felipemalli.dronefeeder.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * VideoRepository for handling video entity storage.
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
