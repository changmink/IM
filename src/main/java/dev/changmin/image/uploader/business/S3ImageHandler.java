package dev.changmin.image.uploader.business;

import dev.changmin.image.uploader.model.ImageInfo;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class S3ImageHandler implements ImageHandler{
    private String buketName = "images-my";
    private String directory = "images";
    private static S3AsyncClient s3Client = S3AsyncClient.builder()
                    .build();
    @Override
    public Mono<String> writeImage(ImageInfo imageInfo) {
        String imagePath = ImagePathGenerator.get(directory);
        CompletableFuture future = s3Client.putObject(
                PutObjectRequest
                        .builder()
                        .bucket(buketName)
                        .key(imagePath)
                        .contentType(imageInfo.getMediaType().toString())
                        .contentLength(imageInfo.getLength())
                        .build(),
                AsyncRequestBody.fromPublisher(imageInfo.getData())
        );
        return Mono.fromFuture(future)
                .map(resp -> {
                    return imagePath;
                });
    }
}
