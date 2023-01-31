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
    public Mono<String> writeImage(FilePart imageFilePart) {
        File file = new File(imageFilePart.filename());
        BlobServiceAsyncClient client = new BlobServiceClientBuilder()
                .endpoint(azureBlobEndpoint)
                .buildAsyncClient();
        return imageFilePart.transferTo(file)
                        .doOnSuccess(v -> {
                            client.createBlobContainerIfNotExists("images")
                                .flatMap(blobContainerAsyncClient -> {
                                    return blobContainerAsyncClient.getBlobAsyncClient(imageFilePart.filename())
                                                                .uploadFromFile(file.getPath());
                                }).subscribe();
                        }).thenReturn(file.getPath());
    }
}
