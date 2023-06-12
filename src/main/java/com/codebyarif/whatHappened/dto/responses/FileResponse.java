package com.codebyarif.whatHappened.dto.responses;

import com.codebyarif.whatHappened.models.File;
import lombok.Data;

@Data
public class FileResponse {

    private String name;

    private String fileType;

    public FileResponse(File file) {
        this.name =file.getName();
        this.fileType = file.getFileType();
    }

}
