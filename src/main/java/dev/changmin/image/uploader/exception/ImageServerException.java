package dev.changmin.image.uploader.exception;

public class ImageServerException extends RuntimeException{
    private Integer code;
    private String message;

    public ImageServerException(ResponseData responseData) {
        this.code = responseData.code;
        this.message = responseData.message;
    }
    public ImageServerException(ResponseData responseData, Throwable cause) {
        super(cause);
        this.code = responseData.code;
        this.message = responseData.message;
    }
}
