package dev.changmin.image.uploader.exception;

public enum ResponseData {
    SUCCESS(0, "Success"),
    NOT_EXIST(40001, "Not Exist"),
    NOT_IMAGE(40002, "Not Image");
    public Integer code;
    public String message;

    ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
