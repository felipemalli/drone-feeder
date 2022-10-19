package io.github.nataelienai.dronefeeder.video;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
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

}
