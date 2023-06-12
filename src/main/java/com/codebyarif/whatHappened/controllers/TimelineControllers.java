package com.codebyarif.whatHappened.controllers;

import com.codebyarif.whatHappened.dto.requests.TimelineRequest;
import com.codebyarif.whatHappened.dto.responses.TimelineResponse;
import com.codebyarif.whatHappened.dto.responses.TimelineWithMomentResponse;
import com.codebyarif.whatHappened.services.TimelineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TimelineControllers {

    private final TimelineService timelineService;

    public TimelineControllers(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @PostMapping("/timelines")
    public ResponseEntity<?> saveTimeline(@RequestBody TimelineRequest timeline) {
        timelineService.saveTimeline(timeline);
        return new ResponseEntity("Timeline is saved.", HttpStatus.CREATED);
    }

    @GetMapping("/timelines")
    public Page<TimelineResponse> getTimelines (Pageable page) {
        return timelineService.getTimelines(page).map(TimelineResponse::new);
    }

    @GetMapping("/timelines/{id:[0-9]+}")
    public TimelineWithMomentResponse getTimelineMoment(@PathVariable long id) {
        return timelineService.getTimelineMoment(id);
    }

    @PutMapping("/{userId}/timelines/{id:[0-9]+}")
    public TimelineResponse updateTimeline(@RequestBody TimelineRequest updatedTimeline, @PathVariable long userId,
                                           @PathVariable long id) {
        return timelineService.updateTimeline(updatedTimeline, id);
    }

    @DeleteMapping("/timelines/{id:[0-9]+}")
    public String deleteTimeline(@PathVariable long id) {
        return timelineService.deleteTimeline(id);
    }

}
