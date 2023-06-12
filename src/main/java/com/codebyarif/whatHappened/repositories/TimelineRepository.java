package com.codebyarif.whatHappened.repositories;

import com.codebyarif.whatHappened.models.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

}
