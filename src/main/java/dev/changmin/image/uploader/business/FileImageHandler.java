package dev.changmin.image.uploader.business;

import dev.changmin.image.uploader.exception.NotExistException;
import dev.changmin.image.uploader.model.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.ByteBuffer;
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
        Path imagePath  = Paths.get(path);
        File imageFile = imagePath.toFile();
        if (!imageFile.exists() && !imageFile.isFile()) {
            throw new NotExistException();
        }
        Flux<ByteBuffer> imageData = DataBufferUtils.read(imagePath, new DefaultDataBufferFactory(), 4096)
        .map(dataBuffer -> dataBuffer.asByteBuffer());
        ImageInfo imageInfo = new ImageInfo(imageData, MediaType.ALL, imageFile.length());
        return Mono.just(imageInfo);
    }
}
