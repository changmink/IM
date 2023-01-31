package dev.changmin.image.uploader.controller;

import dev.changmin.image.uploader.business.S3ImageHandler;
import dev.changmin.image.uploader.model.ImageInfo;
import dev.changmin.image.uploader.model.ResponseForm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

@RestController
@RequestMapping("/images")
public class ImageController {
    private S3ImageHandler imageHandler;

    public ImageController(S3ImageHandler imageHandler) {
        this.imageHandler = imageHandler;
    }

    @PostMapping
    public Mono<ResponseForm> upload(@RequestHeader HttpHeaders headers, @RequestBody Flux<ByteBuffer> body) {
        long length = headers.getContentLength();
        MediaType mediaType = headers.getContentType();
        return imageHandler.writeImage(new ImageInfo(body, mediaType, length))
                .map(path -> new ResponseForm("Success", 0, path));
    }
}
