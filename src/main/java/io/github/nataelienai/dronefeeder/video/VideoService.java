package io.github.nataelienai.dronefeeder.video;

import io.github.nataelienai.dronefeeder.delivery.exception.DeliveryNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * VideoService.
 */
@Service
public class VideoService {

  private final VideoRepository videoRepository;

  @Autowired
  public VideoService(VideoRepository videoRepository) {
    this.videoRepository = videoRepository;
  }

  /**
   * upload.
   */
  @Transactional
  public Video upload(MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    Video video = new Video();
    Long size = file.getSize();
    video.setSize(size);
    video.setBase64(Base64.getEncoder().encodeToString(file.getBytes()));
    video.setFileName(fileName);
    videoRepository.save(video);
    return video;
  }

  /**
   * download.
   */
  public byte[] download(Long id) {
    Optional<Video> video = videoRepository.findById(id);
    if (video.isEmpty()) {
      throw new DeliveryNotFoundException("Video not found.");
    }
    String base64 = video.get().getBase64();
    return Base64.getDecoder().decode(base64.getBytes());
  }

}
