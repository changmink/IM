package dev.changmin.image.uploader.business;

import dev.changmin.image.uploader.exception.DuplicateFileNameException;
import dev.changmin.image.uploader.exception.NotExistException;
import dev.changmin.image.uploader.exception.NotImageException;
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
            throw new NotExistException();
        }

        File imageFile = new File(imageFilePart.filename());

        if (imageChecker.isNotAvailableImage(imageFile)) {
            throw new NotImageException();
        }

        return imageFilePart.transferTo(imageFile)
                .doOnSuccess(v -> {
                    ImageHandler imageHandler = imageHandlerManager.get(service);
                    imageHandler.writeImage(imageFile);
                    imageFile.delete();
                });
    }
}
