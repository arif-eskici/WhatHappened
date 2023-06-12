package com.codebyarif.whatHappened.services;

import com.codebyarif.whatHappened.dto.requests.MomentRequest;
import com.codebyarif.whatHappened.dto.responses.MomentResponse;
import com.codebyarif.whatHappened.dto.responses.MomentWithFileResponse;
import com.codebyarif.whatHappened.errors.NotFoundException;
import com.codebyarif.whatHappened.models.File;
import com.codebyarif.whatHappened.models.Moment;
import com.codebyarif.whatHappened.models.Timeline;
import com.codebyarif.whatHappened.repositories.FileRepository;
import com.codebyarif.whatHappened.repositories.MomentRepository;
import com.codebyarif.whatHappened.repositories.TimelineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MomentService {

    private final MomentRepository momentRepository;
    private final TimelineRepository timelineRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;

    public MomentService(MomentRepository momentRepository, TimelineRepository timelineRepository,
                         FileRepository fileRepository, FileService fileService) {
        this.momentRepository = momentRepository;
        this.timelineRepository = timelineRepository;
        this.fileRepository = fileRepository;
        this.fileService = fileService;
    }

    public void saveMoment(MomentRequest momentRequest) throws ParseException {
        Moment moment = new Moment();
        moment.setTitle(momentRequest.getTitle());
        moment.setDescription(momentRequest.getDescription());
        moment.setCreationDate(new Date());
        Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(momentRequest.getMomentDate().substring(0, 16));
        moment.setMomentDate(date);
        moment.setTimeline(timelineRepository.getReferenceById(momentRequest.getTimelineId()));

        if(momentRequest.getAttachmentId() != null) {
            for(long attachmentId : momentRequest.getAttachmentId()) {
                Optional<File> optionalFileAttachment = fileRepository.findById(attachmentId);
                if(optionalFileAttachment.isPresent()) {
                    File fileAttachment = optionalFileAttachment.get();
                    fileAttachment.setMoment(moment);
                    fileRepository.save(fileAttachment);
                }
            }
        }
        momentRepository.save(moment);
    }

    public Page<Moment> getMoments(Pageable page) {
       return momentRepository.findAll(page);
    }

    public List<MomentWithFileResponse> getMomentOfTimeline(long timelineId) {
        Timeline timelineDB = timelineRepository.getReferenceById(timelineId);
        List<Moment> moments = momentRepository.findByTimeline(timelineDB);
        List<MomentWithFileResponse> momentResponse = new ArrayList<>();

        for(int i = 0; i < moments.size(); i++) {
            momentResponse.add(new MomentWithFileResponse(moments.get(i)));
        }
        return momentResponse;

    }
    public Page<Moment> getMomentOfTimelineOfUser(Pageable page, long timelineId) {
        Timeline timeline = timelineRepository.getReferenceById(timelineId);
        return momentRepository.findByTimeline(timeline, page);
    }

    public MomentResponse updateMoment(MomentRequest updatedMoment, long id) throws ParseException {

        Optional<Moment> optionalMoment = momentRepository.findById(id);
        if(optionalMoment.isEmpty()) {
            throw new NotFoundException(id);
        }
        Moment momentDB = new Moment();
        momentDB.setTitle(updatedMoment.getTitle());
        momentDB.setDescription(updatedMoment.getDescription());
        momentDB.setCreationDate(new Date());
        Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(updatedMoment.getMomentDate().substring(0,16));
        momentDB.setMomentDate(date);
        momentDB.setTimeline(optionalMoment.get().getTimeline());

        if(updatedMoment.getAttachmentId() != null) {
            for(long attachmentId : updatedMoment.getAttachmentId()) {
                Optional<File> optionalFileAttachment = fileRepository.findById(attachmentId);
                if(optionalFileAttachment.isPresent()) {
                    File fileAttachment = optionalFileAttachment.get();
                    fileAttachment.setMoment(momentDB);
                    fileRepository.save(fileAttachment);
                }
            }
        }
        momentRepository.deleteById(id);
        momentRepository.save(momentDB);
        return new MomentResponse(momentDB);
    }

     public String deleteMoment(long id) {
        Optional<Moment> optionalMoment = momentRepository.findById(id);
        if(optionalMoment.isPresent()) {
            if(optionalMoment.get().getAttachments() != null && !optionalMoment.get().getAttachments().isEmpty()) {
                for(int i = 0; i < optionalMoment.get().getAttachments().size(); i++) {
                    String fileName = optionalMoment.get().getAttachments().get(i).getName();
                    fileService.deleteAttachment(fileName);
                }
            }
            momentRepository.deleteById(id);
            return "Moment with id " + id + " has been deleted success.";
        }
        throw new NotFoundException(id);
    }
}
