package com.codebyarif.whatHappened.dto.requests;

import lombok.Data;

@Data
public class TimelineRequest {

    private long userId;

    private String title;

    private String description;

    private String [] tags;
}
