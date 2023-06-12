package com.codebyarif.whatHappened.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Moment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long momentId;

    private String title;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date momentDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "moment", cascade = CascadeType.REMOVE)
    private List<File> attachments;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date creationDate;

    @ManyToOne
    private Timeline timeline;

}
