package io.github.nataelienai.dronefeeder.video;

import io.github.nataelienai.dronefeeder.delivery.exception.DeliveryNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for FileUploadController.
 */
@RestController
public class FileUploadController {

  private final VideoRepository videoRepository;

  @Autowired
  public FileUploadController(VideoRepository videoRepository) {
    this.videoRepository = videoRepository;
  }

  /**
   * uploadFile.
   */
  @PostMapping("/uploadVideo")
  @ResponseStatus(HttpStatus.OK)
  public Video uploadVideo(@RequestBody MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

    Video video = new Video();
    Long size = file.getSize();
    video.setBase64(Base64.getEncoder().encodeToString(file.getBytes()));
    video.setFilename(fileName);
    video.setSize(size);

    videoRepository.save(video);
    return video;
  }

  /**
   * downloadFile.
   */
  @PostMapping("/downloadVideo/{videoId}")
  public ResponseEntity<?> downloadVideo(@PathVariable Long videoId) throws IOException {

    Optional<FileUploadResponse> video = fileRepository.findById(videoId);
    if (video.isEmpty()) {
      throw new DeliveryNotFoundException("Video not found.");
    }

    String base64 = video.get().getBase64();

    byte[] decodedVideo = Base64.getDecoder().decode(base64.getBytes());

    return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.parseMediaType("video/mp4"))
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=video_%s.%s", videoId, "mp4"))
            .body(decodedVideo);
  }

}
