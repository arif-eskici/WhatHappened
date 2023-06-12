package com.codebyarif.whatHappened.controllers;

import com.codebyarif.whatHappened.dto.requests.MomentRequest;
import com.codebyarif.whatHappened.dto.responses.MomentResponse;
import com.codebyarif.whatHappened.services.MomentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1")
public class MomentController {

    private final MomentService momentService;

    public MomentController(MomentService momentService) {
        this.momentService = momentService;
    }

    @PostMapping("/moments")
    public ResponseEntity<?> saveMoment(@RequestBody MomentRequest momentRequest) throws ParseException {
        momentService.saveMoment(momentRequest);
        return new ResponseEntity("Moment is saved", HttpStatus.CREATED);
    }

    @GetMapping("/moments")
    public Page<MomentResponse> getMoments(Pageable page) {
        return momentService.getMoments(page).map(MomentResponse::new);
    }

    @GetMapping("/users/{userId}/timelines/{id:[0-9]+}/moments")
    public Page<MomentResponse> getMomentOfTimelineOfUser (Pageable page, @PathVariable long userId,
                                                           @PathVariable long id) {
        return momentService.getMomentOfTimelineOfUser(page, id).map(MomentResponse::new);
    }

    @PutMapping("/users/{userId}/moments/{id:[0-9]+}")
    public MomentResponse updateMoment(@RequestBody MomentRequest updatedMoment, @PathVariable long userId,
                                       @PathVariable long id) throws ParseException {
        return momentService.updateMoment(updatedMoment, id);
    }

    @DeleteMapping("/moments/{id:[0-9]+}")
    public String deleteMoment(@PathVariable long id) {
        return momentService.deleteMoment(id);
    }

}
