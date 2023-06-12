package com.codebyarif.whatHappened.services;

import com.codebyarif.whatHappened.config.AppConfig;
import com.codebyarif.whatHappened.models.File;
import com.codebyarif.whatHappened.models.User;
import com.codebyarif.whatHappened.repositories.FileRepository;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final AppConfig appConfig;
    private final Tika tika;

    public FileService(FileRepository fileRepository, AppConfig appConfig) {
        this.fileRepository = fileRepository;
        this.appConfig = appConfig;
        this.tika = new Tika();
    }
    public File saveMomentAttachment(MultipartFile file) {
        String fileName = generateRandomName();
        java.io.File target = new java.io.File(appConfig.getAttachmentStoragePath() + "/" + fileName);
        String fileType = null;
        try {
            byte[] arr = file.getBytes();
            OutputStream outputStream = new FileOutputStream(target);
            outputStream.write(arr);
            outputStream.close();
            fileType = detectType(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File attachment = new File();
        attachment.setName(fileName);
        attachment.setDate(new Date());
        attachment.setFileType(fileType);
        return fileRepository.save(attachment);
    }
    private String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    private String detectType(byte[] arr) {
        return tika.detect(arr);
    }

    public void deleteAllStoredFilesForUser(User userDB) {
        List<File> filesToBeRemoved = fileRepository.findByMomentTimelineUser(userDB);
        for(File file : filesToBeRemoved) {
            deleteAttachment(file.getName());
        }
    }
    public void deleteAttachment(String name) {
        if(name == null) {
            return;
        }
        deleteFile(Paths.get(appConfig.getAttachmentStoragePath(), name));
    }
    private void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
