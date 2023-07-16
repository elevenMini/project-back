package com.eleven.miniproject.board.util;

import com.eleven.miniproject.board.entity.UploadImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {

    @Value("${file.dir}")
    private String fileDir;

    // 파일 전체경로
    public String getFullPath(String filename) {
        return fileDir + filename;
    }



    public UploadImage storeImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        // 실제 이름
        String originalImageName = multipartFile.getOriginalFilename();

        // 서버에 저장하는 파일명
        String storeImageName = createStoreImageName(originalImageName);

        // 저장소에 파일 저장
        multipartFile.transferTo(new File(getFullPath(storeImageName)));

        return new UploadImage(originalImageName, storeImageName);
    }

    private String createStoreImageName(String originalImageName) {
        String extName = extractExt(originalImageName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extName;
    }

    private String extractExt(String originalImageName) {
        int pos = originalImageName.lastIndexOf(".");
        String extName = originalImageName.substring(pos + 1);
        return extName;
    }
}
