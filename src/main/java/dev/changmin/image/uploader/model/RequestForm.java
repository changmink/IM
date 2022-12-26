package dev.changmin.image.uploader.model;

import org.springframework.http.codec.multipart.FilePart;

public class RequestForm {
    private String service;
    private FilePart image;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public FilePart getImage() {
        return image;
    }

    public void setImage(FilePart image) {
        this.image = image;
    }
}
