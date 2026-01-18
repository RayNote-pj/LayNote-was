package com.project.lay_note_was.lay_note.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${root.path}")
    private String projectPath;

    public String convertImgFile(MultipartFile file, String subPath) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String originalFileName = file.getOriginalFilename();
        String newImgName = UUID.randomUUID() + "_" + originalFileName;

        String rootPath = projectPath + "/image/";
        String filePath = subPath + "/" + newImgName;

        File dir = new File(rootPath + subPath);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create directory: " + dir.getAbsolutePath());
            }
        }

        Path uploadPath = Paths.get(rootPath + filePath);

        try {
            file.transferTo(uploadPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Fail to save file", e);
        }

        return filePath;
    }
}
