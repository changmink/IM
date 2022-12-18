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
    @Override
    public Mono<Void> writeImage(FilePart imageFilePart) {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                                            .build();
        try {
            File file = new File(imageFilePart.filename());
            return imageFilePart.transferTo(file)
                            .doOnSuccess(s -> {
                                s3.putObject("images-my", "images/" + imageFilePart.filename(), file);
                            });

        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }
}
