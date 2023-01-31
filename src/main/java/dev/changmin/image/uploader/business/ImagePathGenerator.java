package dev.changmin.image.uploader.business;

import java.util.UUID;

public class ImagePathGenerator {
    public static String get(String directory) {
        return String.join("/", directory, UUID.randomUUID().toString());
    }
}
