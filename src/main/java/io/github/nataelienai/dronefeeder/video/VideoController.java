package io.github.nataelienai.dronefeeder.video;

import io.github.nataelienai.dronefeeder.delivery.exception.DeliveryNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for VideoController.
 */
@RestController
@RequestMapping("/video")
public class VideoController {

  private final VideoRepository videoRepository;

  @Autowired
  public VideoController(VideoRepository videoRepository) {
    this.videoRepository = videoRepository;
  }

  /**
   * uploadFile.
   */
  @PostMapping("/upload")
  @ResponseStatus(HttpStatus.OK)
  public Video upload(@RequestBody MultipartFile file) throws IOException {
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
   * downloadFile.
   */
  @PostMapping("/download/{id}")
  public ResponseEntity<?> downloadVideo(@PathVariable Long id) {

    Optional<Video> video = videoRepository.findById(id);
    if (video.isEmpty()) {
      throw new DeliveryNotFoundException("Video not found.");
    }

    String base64 = video.get().getBase64();

    byte[] decodedVideo = Base64.getDecoder().decode(base64.getBytes());

    return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.parseMediaType("video/mp4"))
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=video_%s.%s", id, "mp4"))
            .body(decodedVideo);
  }

}
