package dev.changmin.image.uploader.business;

import dev.changmin.image.uploader.model.ImageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.core.async.ResponsePublisher;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.concurrent.CompletableFuture;

@Service
public class S3ImageHandler implements ImageHandler{
    private String buketName = "images-my";
    private String directory = "images";
    private static S3AsyncClient s3Client = S3AsyncClient.builder()
                    .build();

    private static Logger logger = LoggerFactory.getLogger(S3ImageHandler.class);
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

    public Mono<ImageInfo> getImage(String path) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(buketName)
                .key(path)
                .build();

        return Mono.fromFuture(s3Client.getObject(getObjectRequest, AsyncResponseTransformer.toPublisher()))
                .map(resp -> new ImageInfo(Flux.from(resp), MediaType.valueOf(resp.response().contentType()), resp.response().contentLength()));
    }
}
