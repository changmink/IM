package dev.changmin.image.uploader.business;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;

@Service
public class S3ImageHandler implements ImageHandler{
    private String buketName = "images-my";
    private String directory = "images";

    @Override
    public Mono<String> writeImage(FilePart imageFilePart) {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                                            .build();
        try {
            File file = new File(imageFilePart.filename());
            String path = directory + "/" + imageFilePart.filename();
            return imageFilePart.transferTo(file)
                            .doOnSuccess(s -> {
                                s3.putObject(buketName, path, file);
                            }).thenReturn(path);

        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }
}
