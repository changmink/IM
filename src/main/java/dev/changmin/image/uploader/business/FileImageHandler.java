package dev.changmin.image.uploader.business;

import dev.changmin.image.uploader.exception.DuplicateFileNameException;
import dev.changmin.image.uploader.exception.NotExistException;
import dev.changmin.image.uploader.exception.NotImageException;
import dev.changmin.image.uploader.model.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
    public Mono<String> writeImage(ImageInfo imageInfo){
        String writePath = ImagePathGenerator.get(basePath);
        Path path = Paths.get(writePath);

        if (imageInfo.getLength() < 0) {
            throw new NotExistException();
        }

        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
        return DataBufferUtils.write(imageInfo.getData()
                .map(d -> dataBufferFactory.wrap(d)), path, StandardOpenOption.CREATE)
                .thenReturn(writePath);
    }

    @Override
    public Mono<ImageInfo> getImage(String path) {
        // 구현 필요
        return null;
    }
}
