package com.codebyarif.whatHappened.dto.requests;

import lombok.Data;

@Data
public class MomentRequest {

    private String title;

    private String description;

    private String momentDate;

    private long timelineId;

    private long[] attachmentId;


}
