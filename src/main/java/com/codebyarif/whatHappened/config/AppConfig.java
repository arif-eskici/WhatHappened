package com.codebyarif.whatHappened.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "what-happen")
public class AppConfig {

    private String uploadPath;

    private String attachmentStorage = "attachments";

    public String getAttachmentStoragePath() {
        return uploadPath + "/" + attachmentStorage;
    }
}
