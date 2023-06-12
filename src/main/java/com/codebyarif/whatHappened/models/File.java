package com.codebyarif.whatHappened.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;

    private String name;

    private String fileType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    private Moment moment;
}
