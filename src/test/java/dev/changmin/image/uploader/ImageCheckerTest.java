package dev.changmin.image.uploader;

import dev.changmin.image.uploader.business.ImageChecker;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageCheckerTest extends ImageUploaderApplicationTests{
    private ImageChecker imageChecker = new ImageChecker();

    @Test
    public void isNotExistFile() {
        Path writePath = Path.of(String.join("/", basePath, "NotExistFile"));
        assertFalse(imageChecker.isExist(writePath));
    }

    @Test
    public void isExistFile() throws IOException {
        File tempfile = File.createTempFile("temp", ".jpg", new File(basePath));
        Path writePath = Path.of(String.join("/", basePath, tempfile.getName()));
        assertTrue(imageChecker.isExist(writePath));
        tempfile.deleteOnExit();
    }

    @Test
    public void isAvailable() throws IOException{
        File jpgfile = File.createTempFile("temp", ".jpg", new File(basePath));
        Path jpgPath = Path.of(String.join("/", basePath, jpgfile.getName()));
        assertFalse(imageChecker.isNotAvailableImage(jpgPath));

        File pngfile = File.createTempFile("temp", ".png", new File(basePath));
        Path pngPath = Path.of(String.join("/", basePath, pngfile.getName()));
        assertFalse(imageChecker.isNotAvailableImage(pngPath));

        jpgfile.deleteOnExit();
        pngfile.deleteOnExit();
    }

    @Test
    public void isNotAvailable() throws IOException{
        File txtfile = File.createTempFile("temp", ".txt", new File(basePath));
        Path txtPath = Path.of(String.join("/", basePath, txtfile.getName()));
        assertTrue(imageChecker.isNotAvailableImage(txtPath));

        txtfile.deleteOnExit();
    }
}
