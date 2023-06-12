package com.codebyarif.whatHappened.repositories;

import com.codebyarif.whatHappened.models.File;
import com.codebyarif.whatHappened.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByMomentTimelineUser(User user);
}
