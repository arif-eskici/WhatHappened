package com.codebyarif.whatHappened.dto.responses;

import com.codebyarif.whatHappened.models.Moment;
import lombok.Data;

@Data
public class MomentResponse {

    private long momentId;

    private String title;

    private String description;

    private String momentDate;

    private String creationDate;

    public MomentResponse (Moment moment) {
        this.momentId = moment.getMomentId();
        this.title = moment.getTitle();
        this.description = moment.getDescription();
        this.momentDate = moment.getMomentDate().toString();
        this.creationDate = moment.getCreationDate().toString();

    }
}
