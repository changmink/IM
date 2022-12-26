package dev.changmin.image.uploader.controller;

import dev.changmin.image.uploader.business.*;
import dev.changmin.image.uploader.model.RequestForm;
import dev.changmin.image.uploader.model.ResponseForm;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/image")
public class ImageController {
    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public Mono<ResponseForm> upload(Mono<RequestForm> request) {
            return request.flatMap(req -> {
                return imageService.writeImage(req);
            }).thenReturn(new ResponseForm("Success", 0));
    }
}
