package dev.changmin.image.uploader.model;

import java.util.Map;

public class ResponseForm {
    private String message;
    private Integer code;

    private String path;

    public ResponseForm(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public ResponseForm(String message, Integer code, String path) {
        this.message = message;
        this.code = code;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }
}
