package dev.changmin.image.uploader.business;

import dev.changmin.image.uploader.exception.ImageServerException;
import dev.changmin.image.uploader.exception.ResponseData;
import dev.changmin.image.uploader.model.RequestForm;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;

@Service
public class ImageService {
    private ImageChecker imageChecker;

    private ImageHandlerManager imageHandlerManager;
    public ImageService(ImageChecker imageChecker, ImageHandlerManager imageHandlerManager) {
        this.imageChecker = imageChecker;
        this.imageHandlerManager = imageHandlerManager;
    }
    public Mono<Void> writeImage(RequestForm requestForm) {
        FilePart imageFilePart = requestForm.getImage();
        String service = requestForm.getService();
        if (null == service || imageFilePart.filename().isEmpty()) {
            throw new ImageServerException(ResponseData.NOT_EXIST);
        }

        File imageFile = new File(imageFilePart.filename());

        if (imageChecker.isNotAvailableImage(imageFile)) {
            throw new ImageServerException(ResponseData.NOT_IMAGE);
        }

        return imageFilePart.transferTo(imageFile)
                .doOnSuccess(v -> {
                    ImageHandler imageHandler = imageHandlerManager.get(service);
                    imageHandler.writeImage(imageFile);
                    imageFile.delete();
                });
    }
}
