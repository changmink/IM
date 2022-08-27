package dev.changmin.image.uploader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageUploaderApplicationTests {
    @Value("${server.webflux.base-path}")
    protected String basePath;

    @Test
    void contextLoads() {
    }

}
