package io.github.felipemalli.dronefeeder.video;

import io.github.felipemalli.dronefeeder.delivery.Delivery;
import io.github.felipemalli.dronefeeder.delivery.DeliveryService;
import io.github.felipemalli.dronefeeder.video.exception.VideoAlreadyLinkedException;
import io.github.felipemalli.dronefeeder.video.exception.VideoInvalidNameException;
import io.github.felipemalli.dronefeeder.video.exception.VideoNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service for handling business logic of videos.
 */
@Service
public class VideoService {

  private final VideoRepository videoRepository;
  private final DeliveryService deliveryService;

  @Autowired
  public VideoService(VideoRepository videoRepository, DeliveryService deliveryService) {
    this.videoRepository = videoRepository;
    this.deliveryService = deliveryService;
  }

  /**
   * Find name of the file.
   *
   * @param file the video file to upload.
   * @return the video file name.
   * @throws VideoInvalidNameException if the video file name is invalid.
   */
  public String findName(MultipartFile file) {
    String fileName = file.getOriginalFilename();
    if (fileName == null) {
      throw new VideoInvalidNameException("Video name in invalid format.");
    }
    return StringUtils.cleanPath(fileName);
  }

  /**
   * Retrieves all videos.
   *
   * @return all videos.
   */
  public List<Video> findAll() {
    return videoRepository.findAll();
  }

  /**
   * Upload the video.
   *
   * @param file the video file to upload.
   * @param deliveryId the id of the delivery associate with the video.
   * @return the uploaded video file.
   * @throws IOException in case of an access error.
   * @throws VideoAlreadyLinkedException when video is already linked with a delivery.
   */
  @Transactional
  public Video upload(MultipartFile file, Long deliveryId) throws IOException {
    Video video = new Video();
    String fileName = findName(file);
    Long size = file.getSize();
    video.setFileName(fileName);
    video.setSize(size);
    video.setBase64(Base64.getEncoder().encodeToString(file.getBytes()));
    Delivery delivery = deliveryService.findById(deliveryId);
    if (delivery.getVideoId() != null) {
      throw new VideoAlreadyLinkedException("Delivery already has a video.");
    }
    video.setDelivery(delivery);
    Video savedVideo = videoRepository.save(video);
    Long id = savedVideo.getId();
    delivery.setVideoId(id);
    return video;
  }

  /**
   * Download the video.
   *
   * @param id the id of the file video to download.
   * @return the video file for download.
   * @throws VideoNotFoundException if a video with {@literal id} does not exist.
   */
  public byte[] download(Long id) {
    Optional<Video> video = videoRepository.findById(id);
    if (video.isEmpty()) {
      throw new VideoNotFoundException("Video not found.");
    }
    String base64 = video.get().getBase64();
    return Base64.getDecoder().decode(base64.getBytes());
  }

}
