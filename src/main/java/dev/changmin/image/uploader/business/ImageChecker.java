package dev.changmin.image.uploader.business;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

/*
* 이미지가 정상(모든 제약 조건 만족)하는지 확인하는 클래스
* Files 같은 statc 메서드를 사용하므로 의존성 주입을 위해 별도 클래스로 분리한다.
* */
@Component
public class ImageChecker {
    public boolean isExist(Path path) {
        return Files.exists(path);
    }

    // 추후에 라이브러리 통해서 파일 헤더 확인 예정
    public boolean isNotAvailableImage(Path path) {
        String fileName = path.toString();
        return !(
                    fileName.endsWith(".jpg") ||
                    fileName.endsWith(".png")
                );
    }
}
