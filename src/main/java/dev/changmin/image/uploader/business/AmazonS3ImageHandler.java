package dev.changmin.image.uploader.business;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;

@Service
public class AmazonS3ImageHandler implements ImageHandler{
    @Value("${AMAZON_S3_BUCKET}")
    private String amazonS3Bucket;


    @Override
    public Mono<Void> writeImage(File imageFile) {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                                            .build();
        try {
            s3.putObject(amazonS3Bucket, "images/" + imageFile.getName(), imageFile);
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
