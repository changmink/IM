package dev.changmin.image.uploader.business;

import dev.changmin.image.uploader.exception.DuplicateFileNameException;
import dev.changmin.image.uploader.exception.NotExistException;
import dev.changmin.image.uploader.exception.NotImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

@Service
public class FileImageHandler implements ImageHandler {
    @Value("${server.webflux.base-path}")
    private String basePath;
    private ImageChecker imageChecker;

    @Autowired
    public FileImageHandler(ImageChecker imageChecker) {
        this.imageChecker = imageChecker;
    }

    @Override
    public Mono<String> writeImage(FilePart imageFilePart){
        Path writePath = Path.of(String.join("/", basePath, imageFilePart.filename()));
        if (imageChecker.isExist(writePath)) {
            throw new DuplicateFileNameException();
        }

        if (imageFilePart.filename().isEmpty()) {
            throw new NotExistException();
        }

        if (imageChecker.isNotAvailableImage(writePath)) {
            throw new NotImageException();
        }

        return imageFilePart.transferTo(writePath).thenReturn(writePath.toString());
    }
}
