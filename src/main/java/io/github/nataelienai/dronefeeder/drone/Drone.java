package io.github.nataelienai.dronefeeder.drone;

import io.github.nataelienai.dronefeeder.delivery.Delivery;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

/**
 * Entity of a drone model.
 */
@Entity
@Table(name = "drone")
public class Drone {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "latitude", nullable = false)
  private String latitude;

  @Column(name = "longitude", nullable = false)
  private String longitude;

  @OneToMany(mappedBy = "drone", fetch = FetchType.LAZY)
  private List<Delivery> deliveries;

  public Long getId() {
    return id;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  @PreRemove
  private void preRemove() {
    if (deliveries != null) {
      deliveries.forEach(delivery -> delivery.setDrone(null));
    }
  }
}
