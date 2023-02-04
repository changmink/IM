package dev.changmin.image.uploader.business;

import dev.changmin.image.uploader.exception.ImageServerException;
import dev.changmin.image.uploader.exception.ResponseData;
import org.springframework.stereotype.Service;

@Service
public class ImageHandlerManager {
    private ImageServiceConfig imageServiceConfig;
    private AmazonS3ImageHandler amazonS3ImageHandler;
    private AzureImageHandler azureImageHandler;
    private GoogleStorageImageHandler googleStorageImageHandler;

    public ImageHandlerManager(ImageServiceConfig imageServiceConfig, AmazonS3ImageHandler amazonS3ImageHandler, AzureImageHandler azureImageHandler, GoogleStorageImageHandler googleStorageImageHandler) {
        this.imageServiceConfig = imageServiceConfig;
        this.amazonS3ImageHandler = amazonS3ImageHandler;
        this.azureImageHandler = azureImageHandler;
        this.googleStorageImageHandler = googleStorageImageHandler;
    }

    public ImageHandler get(String service) {
        StorageType storageType = imageServiceConfig.getStorage(service);
        if (null == storageType) {
            throw new ImageServerException(ResponseData.NOT_EXIST);
        }

        if (storageType.equals(StorageType.AMAZON)) {
            return amazonS3ImageHandler;
        }

        if (storageType.equals(StorageType.AZURE)) {
            return azureImageHandler;
        }

        //if (storageType.equals(StorageType.GOOGLE)) {
            return googleStorageImageHandler;
        //}
    }
}
