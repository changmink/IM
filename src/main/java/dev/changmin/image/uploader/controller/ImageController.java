package dev.changmin.image.uploader.controller;

import dev.changmin.image.uploader.business.S3ImageHandler;
import dev.changmin.image.uploader.model.ImageInfo;
import dev.changmin.image.uploader.model.ResponseForm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.Optional;

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

    @GetMapping(path = {"/{folder}/{path}", "/{path}"})
    public Mono<ResponseEntity<Flux<ByteBuffer>>> download(@PathVariable("folder") Optional<String> folder, @PathVariable("path") String path) {
        String dir = (folder.isPresent())? folder.get() + "/" + path : path;
        return imageHandler.getImage(dir).map(resp -> {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE,resp.response().contentType())
                    .body(Flux.from(resp));
        });
    }
}
