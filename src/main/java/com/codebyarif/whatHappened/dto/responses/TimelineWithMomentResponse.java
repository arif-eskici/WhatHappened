package com.codebyarif.whatHappened.dto.responses;

import com.codebyarif.whatHappened.models.Timeline;
import lombok.Data;

@Data
public class TimelineWithMomentResponse {

    private String title;

    private String description;

    private long userId;

    private String creationDate;

    private String[] tags;

    private MomentWithFileResponse[] moments;

    public TimelineWithMomentResponse(Timeline timeline) {
        this.title = timeline.getTitle();
        this.description = timeline.getDescription();
        this.userId = timeline.getUser().getUserId();
        this.creationDate = timeline.getCreationDate().toString();

        if(timeline.getTags().length() > 0) {
            this.tags = timeline.getTags().split(",");
        }
    }
}
