package dev.changmin.image.uploader.business;

import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class GoogleStorageImageHandler implements ImageHandler{
    @Value("${GOOGLE_STORAGE_PROJECT_ID}")
    private String googleStorageProjectId;
    @Value("${GOOGLE_STORAGE_BUCKET}")
    private String googleStorageBucket;
    @Value("${GOOGLE_STORAGE_KEY}")
    private String googleStorageKey;

    @Override
    public Mono<Void> writeImage(File imageFile) {
        try {
            InputStream inputStream = new ClassPathResource(googleStorageKey).getInputStream();
            Storage storage = StorageOptions.newBuilder()
                    .setProjectId(googleStorageProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build()
                    .getService();
            BlobId blobId = BlobId.of(googleStorageBucket, "images/" + imageFile.getName());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.createFrom(blobInfo, imageFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
