package io.github.nataelienai.dronefeeder.delivery;

import io.github.nataelienai.dronefeeder.drone.Drone;
import io.github.nataelienai.dronefeeder.video.Video;
import java.time.Instant;
import javax.persistence.CascadeType;
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

  @OneToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "video_id", nullable = true)
  private Video video;

  public Video getVideo() {
    return video;
  }

  public void setVideo(Video video) {
    this.video = video;
  }

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

}
