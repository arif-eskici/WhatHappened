package com.codebyarif.whatHappened.services;

import com.codebyarif.whatHappened.dto.requests.TimelineRequest;
import com.codebyarif.whatHappened.dto.responses.MomentWithFileResponse;
import com.codebyarif.whatHappened.dto.responses.TimelineResponse;
import com.codebyarif.whatHappened.dto.responses.TimelineWithMomentResponse;
import com.codebyarif.whatHappened.errors.NotFoundException;
import com.codebyarif.whatHappened.models.Timeline;
import com.codebyarif.whatHappened.models.User;
import com.codebyarif.whatHappened.repositories.TimelineRepository;
import com.codebyarif.whatHappened.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TimelineService {

    private final TimelineRepository timelineRepository;
    private final UserRepository userRepository;
    private final MomentService momentService;

    public TimelineService(TimelineRepository timelineRepository, UserRepository userRepository,
                           MomentService momentService) {
        this.timelineRepository = timelineRepository;
        this.userRepository = userRepository;
        this.momentService = momentService;
    }

    public TimelineResponse saveTimeline(TimelineRequest timelineRequest) {
        Optional<User> optionalUser = userRepository.findById(timelineRequest.getUserId());
        if(optionalUser.isEmpty()) {
            throw new NotFoundException(timelineRequest.getUserId());
        }

        Timeline timeline = new Timeline();
        timeline.setTitle(timelineRequest.getTitle());
        timeline.setDescription(timelineRequest.getDescription());
        String tags = "";

        for(String tag : timelineRequest.getTags()) {
            tags += tag + ",";
        }

        timeline.setTags(tags);
        timeline.setUser(optionalUser.get());
        timeline.setCreationDate(new Date());
        timelineRepository.save(timeline);
        return new TimelineResponse(timeline);
    }
    public Page<Timeline> getTimelines(Pageable page) {
        return timelineRepository.findAll(page);
    }

    public TimelineResponse updateTimeline(TimelineRequest updatedTimeline, long id) {
        Optional<Timeline> optionalTimeline = timelineRepository.findById(id);
        if(optionalTimeline.isEmpty()) {
            throw new NotFoundException(id);
        }
        Timeline timelineDB = new Timeline();
        timelineDB.setUser(optionalTimeline.get().getUser());
        timelineDB.setTitle(updatedTimeline.getTitle());
        timelineDB.setDescription(updatedTimeline.getDescription());
        String tags = "";

        for(String tag : updatedTimeline.getTags()) {
            tags += tag + ",";
        }

        timelineDB.setTags(tags);
        timelineDB.setCreationDate(new Date());
        timelineRepository.deleteById(id);
        timelineRepository.save(timelineDB);
        return new TimelineResponse(timelineDB);
    }

    public TimelineWithMomentResponse getTimelineMoment(long id) {
        Optional<Timeline> optionalTimeline = timelineRepository.findById(id);
        if(optionalTimeline.isPresent()) {
            TimelineWithMomentResponse timelineWithMomentResponse = new TimelineWithMomentResponse(optionalTimeline.get());
            List<MomentWithFileResponse> momentResponseList = momentService.getMomentOfTimeline(id);
            MomentWithFileResponse[] momentWithFileResponses = new MomentWithFileResponse[momentResponseList.size()];
            momentResponseList.toArray(momentWithFileResponses);
            timelineWithMomentResponse.setMoments(momentWithFileResponses);
            return timelineWithMomentResponse;
        }
        throw new NotFoundException(id);
    }
    public String deleteTimeline(long id) {
        if(timelineRepository.findById(id).isPresent()) {
            timelineRepository.deleteById(id);
            return "Timeline with id " + id + " has been deleted success.";
        }
        throw new NotFoundException(id);
    }
}
