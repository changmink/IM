package dev.changmin.image.uploader.business;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ImageServiceConfig {
    private Map<String, StorageType> config = new HashMap<>();

    public ImageServiceConfig() {
        config.put("my", StorageType.AMAZON);
        config.put("my-image", StorageType.AZURE);
        config.put("my2image", StorageType.GOOGLE);
    }

    public StorageType getStorage(String service) {
        return config.get(service);
    }
}
