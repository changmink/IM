package dev.changmin.image.uploader.model;

import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

import java.nio.ByteBuffer;

public class ImageInfo {
    private Flux<ByteBuffer> data;
    private MediaType mediaType;
    private long length;

    public ImageInfo(Flux<ByteBuffer> data, MediaType mediaType, long length) {
        this.data = data;
        this.mediaType = mediaType;
        this.length = length;
    }

    public Flux<ByteBuffer> getData() {
        return data;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public long getLength() {
        return length;
    }
}
