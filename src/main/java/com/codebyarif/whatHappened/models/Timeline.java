package com.codebyarif.whatHappened.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long timelineId;

    private String title;

    private String description;

    @OneToMany( mappedBy = "timeline", cascade = CascadeType.REMOVE)
    private List<Moment> moments;

    @ManyToOne
    private User user;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    private String tags;

}
