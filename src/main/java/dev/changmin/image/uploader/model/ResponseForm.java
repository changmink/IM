package dev.changmin.image.uploader.model;

public class ResponseForm {
    private String message;
    private Integer code;

    public ResponseForm(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
