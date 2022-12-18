package dev.changmin.image.uploader.controller;

import dev.changmin.image.uploader.business.FileImageHandler;
import dev.changmin.image.uploader.business.ImageHandler;
import dev.changmin.image.uploader.business.S3ImageHandler;
import dev.changmin.image.uploader.model.ResponseForm;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/image")
public class ImageController {
    private ImageHandler imageHandler;

    public ImageController(S3ImageHandler imageHandler) {
        this.imageHandler = imageHandler;
    }

    @PostMapping
    public Mono<ResponseForm> upload(Mono<FilePart> image) {
            return image.flatMap(imageFilePart -> {
                return imageHandler.writeImage(imageFilePart);
            }).thenReturn(new ResponseForm("Success", 0));
    }
}
