package dev.changmin.image.uploader.business;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface ImageHandler {
    Mono<String> writeImage(FilePart imageFilePart);
}
