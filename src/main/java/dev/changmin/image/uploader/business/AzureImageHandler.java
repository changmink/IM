package dev.changmin.image.uploader.business;

import com.azure.storage.blob.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;

@Service
public class AzureImageHandler implements ImageHandler {
    @Value("${AZURE_BLOB_ENDPOINT}")
    private String azureBlobEndpoint;
    @Override
    public Mono<Void> writeImage(File imageFile) {
        BlobServiceAsyncClient client = new BlobServiceClientBuilder()
                .endpoint(azureBlobEndpoint)
                .buildAsyncClient();
        client.createBlobContainerIfNotExists("images")
            .flatMap(blobContainerAsyncClient -> {
                return blobContainerAsyncClient.getBlobAsyncClient(imageFile.getName())
                        .uploadFromFile(imageFile.getPath());
            }).subscribe();
        return null;
    }
}
