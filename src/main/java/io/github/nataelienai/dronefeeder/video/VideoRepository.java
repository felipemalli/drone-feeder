package io.github.nataelienai.dronefeeder.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FileRepository.
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
