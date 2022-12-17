package dev.changmin.image.uploader;

import dev.changmin.image.uploader.business.ImageChecker;
import dev.changmin.image.uploader.business.FileImageHandler;
import dev.changmin.image.uploader.exception.NotExistException;
import dev.changmin.image.uploader.exception.NotImageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.http.codec.multipart.FilePart;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FileImageHandlerTest extends ImageUploaderApplicationTests{
    private ImageChecker imageChecker = mock(ImageChecker.class);
    private FileImageHandler fileImageHandler = new FileImageHandler(imageChecker);

    @Test
    public void success() {
        FilePart filePart = mock(FilePart.class);
        given(filePart.filename()).willReturn("success.jpg");

        given(imageChecker.isExist(any())).willReturn(false);

        given(imageChecker.isNotAvailableImage(any())).willReturn(false);

        fileImageHandler.writeImage(filePart);
    }


    //같은 이름의 파일이 이미 있는경우
    @Test
    public void existFileName() throws IOException {
        File tempfile = File.createTempFile("temp", ".jpg", new File(basePath));

        FilePart filePart = mock(FilePart.class);
        given(filePart.filename()).willReturn(tempfile.getName());

        given(imageChecker.isExist(any())).willReturn(true);

        Assertions.assertThrows(NotExistException.class, () -> {
            fileImageHandler.writeImage(filePart);
        });

        tempfile.deleteOnExit();
    }
    //이미지 파일이 아닌 경우
    @Test
    public void notImageFile() throws IOException{
        File tempfile = File.createTempFile("temp", ".txt", new File(basePath));

        FilePart filePart = mock(FilePart.class);
        given(filePart.filename()).willReturn(tempfile.getName());

        given(imageChecker.isExist(any())).willReturn(false);
        given(imageChecker.isNotAvailableImage(any())).willReturn(true);

        Assertions.assertThrows(NotImageException.class, () -> {
            fileImageHandler.writeImage(filePart);
        });
        tempfile.deleteOnExit();
    }
    //일부만 성공한 경우
    //요청에 이미지가 없는 경우
    //쓰기에 문제가 있는 경우
    //타임 아웃
    //파일 크기가 너무 큰 경우
}
