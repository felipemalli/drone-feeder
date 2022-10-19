package io.github.nataelienai.dronefeeder.video;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

  private final VideoService videoService;

  @Autowired
  public VideoController(VideoService videoService) {
    this.videoService = videoService;
  }

  /**
   * uploadFile.
   */
  @PostMapping("/upload")
  @ResponseStatus(HttpStatus.OK)
  public Video upload(@RequestBody MultipartFile file) throws IOException {
    return videoService.upload(file);
  }

  /**
   * downloadFile.
   */
  @PostMapping("/download/{id}")
  public ResponseEntity<?> download(@PathVariable Long id) {
    byte[] decodedVideo = videoService.download(id);
    return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.parseMediaType("video/mp4"))
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=delivery_video_%s.%s", id, "mp4")
            )
            .body(decodedVideo);
  }

}
