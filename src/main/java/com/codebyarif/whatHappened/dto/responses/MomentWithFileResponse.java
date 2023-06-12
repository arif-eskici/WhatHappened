package com.codebyarif.whatHappened.dto.responses;

import com.codebyarif.whatHappened.models.File;
import com.codebyarif.whatHappened.models.Moment;
import lombok.Data;

@Data
public class MomentWithFileResponse {

    private String title;

    private String description;

    private String momentDate;

    private FileResponse[] attachments;

    private String creationDate;

    public MomentWithFileResponse (Moment moment) {
        this.title = moment.getTitle();
        this.description = moment.getDescription();
        this.momentDate = moment.getMomentDate().toString();
        this.creationDate = moment.getCreationDate().toString();

        if(moment.getAttachments() != null || !moment.getAttachments().isEmpty()) {
            FileResponse[] attachmentResponse = new FileResponse[moment.getAttachments().size()];
            File[] attachment = new File[moment.getAttachments().size()];
            moment.getAttachments().toArray(attachment);
            for(int i = 0; i < attachment.length; i++) {
                attachmentResponse[i] = new FileResponse(attachment[i]);
            }
            this.attachments = attachmentResponse;
        }

    }
}
