package com.codebyarif.whatHappened.repositories;

import com.codebyarif.whatHappened.models.Moment;
import com.codebyarif.whatHappened.models.Timeline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MomentRepository extends JpaRepository<Moment, Long> {
    List<Moment> findByTimeline(Timeline timelineDB);
    Page<Moment> findByTimeline(Timeline timeline, Pageable page);

}
