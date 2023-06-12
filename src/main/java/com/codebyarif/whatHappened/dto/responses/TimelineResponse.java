package com.codebyarif.whatHappened.dto.responses;

import com.codebyarif.whatHappened.models.Timeline;
import lombok.Data;

@Data
public class TimelineResponse {

    private long timelineId;

    private String title;

    private String description;

    private long userId;

    private String creationDate;

    private String[] tags;

    public TimelineResponse(Timeline timeline) {
        this.timelineId = timeline.getTimelineId();
        this.title = timeline.getTitle();
        this.description = timeline.getDescription();
        this.userId = timeline.getUser().getUserId();
        this.creationDate = timeline.getCreationDate().toString();

        if(timeline.getTags().length() > 0) {
            this.tags = timeline.getTags().split(",");
        }
    }

}
