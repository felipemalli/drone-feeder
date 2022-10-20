package io.github.felipemalli.dronefeeder.delivery;

import io.github.felipemalli.dronefeeder.drone.Drone;
import io.github.felipemalli.dronefeeder.video.Video;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity of a delivery model.
 */
@Entity
@Table(name = "delivery")
public class Delivery {

  /**
   * Attributes.
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "status", nullable = false)
  private Integer status;

  @Column(name = "status_last_modified")
  private Instant statusLastModified;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "drone_id", nullable = true)
  private Drone drone;

  @JoinColumn(name = "video_id", insertable = false, updatable = false)
  @OneToOne(targetEntity = Video.class, fetch = FetchType.EAGER, orphanRemoval = true)
  private Video video;

  @Column(name = "video_id")
  private Long videoId;

  /**
   * Methods.
   */

  public Long getId() {
    return id;
  }

  public Status getStatus() {
    return Status.valueOf(status);
  }

  public void setStatus(Status status) {
    this.status = status.getCode();
  }

  public Instant getStatusLastModified() {
    return statusLastModified;
  }

  public void setStatusLastModified(Instant statusLastModified) {
    this.statusLastModified = statusLastModified;
  }

  public Drone getDrone() {
    return drone;
  }

  public void setDrone(Drone drone) {
    this.drone = drone;
  }

  public Long getVideoId() {
    return videoId;
  }

  public void setVideoId(Long videoId) {
    this.videoId = videoId;
  }

}
