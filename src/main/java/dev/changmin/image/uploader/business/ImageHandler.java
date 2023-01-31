package dev.changmin.image.uploader.business;

import dev.changmin.image.uploader.model.ImageInfo;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface ImageHandler {
    Mono<String> writeImage(ImageInfo imageInfo);
    Mono<ImageInfo> getImage(String path);
}
