package com.syncpad.syncpadservice.service;

import com.syncpad.syncpadservice.dto.requests.CreateContentRequest;
import com.syncpad.syncpadservice.dto.requests.AttachContentRequest;
import com.syncpad.syncpadservice.dto.requests.DetachContentRequest;
import com.syncpad.syncpadservice.dto.requests.UpdateContentRequest;
import com.syncpad.syncpadservice.dto.responses.CreateContentResponse;
import com.syncpad.syncpadservice.entity.Content;
import com.syncpad.syncpadservice.entity.ContentTracker;
import com.syncpad.syncpadservice.exceptions.ContentNotFoundException;
import com.syncpad.syncpadservice.exceptions.ContentTrackerNotFoundException;
import com.syncpad.syncpadservice.exceptions.LocationHasNoContentException;
import com.syncpad.syncpadservice.repository.ContentRepository;
import com.syncpad.syncpadservice.repository.ContentTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {
    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentTrackerRepository contentTrackerRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public CreateContentResponse createContent(CreateContentRequest request) {
        Content content = new Content();
        content.setContentBody(request.getContentBody());
        Content savedContent = contentRepository.save(content);

        ContentTracker contentTracker = new ContentTracker();
        contentTracker.setContent(savedContent);
        contentTracker.setLocationId(request.getLocationId());
        contentTrackerRepository.save(contentTracker);

        CreateContentResponse createContentResponse = new CreateContentResponse();
        createContentResponse.setContentId(savedContent.getContentId());

        return createContentResponse;
    }

    public void attachContentToLocation(AttachContentRequest request) {
        Content content = contentRepository.findByContentId(request.getContentId())
                .orElseThrow(() -> new ContentNotFoundException("Content with ID: " + request.getContentId() + " not found."));

            ContentTracker contentTracker = new ContentTracker();
            contentTracker.setLocationId(request.getLocationId());
            contentTracker.setContent(content);
            contentTrackerRepository.save(contentTracker);
    }

    public void updateContent(UpdateContentRequest request) {
        Content content = contentRepository.findByContentId(request.getContentId())
                .orElseThrow(()-> new ContentNotFoundException("Content with ID: " + request.getContentId() + " not found."));

            content.setContentBody(request.getContentBody());
            content.setVersion(content.getVersion() + 1);
            contentRepository.save(content);

            messagingTemplate.convertAndSend("/topic/content-updates/" + content.getContentId(), request.getContentBody());
    }

    public void detachContentFromLocation(DetachContentRequest request){
        List<ContentTracker> contentTrackers = contentTrackerRepository.findByLocationId(request.getLocationId());
        if(!contentTrackers.isEmpty()){
           ContentTracker contentTracker = contentTrackers.stream().
                    filter(cl -> cl.getContent().getContentId().equals(request.getContentId()))
                    .findFirst()
                   .orElseThrow(() -> new LocationHasNoContentException("Location has no content for id: " + request.getContentId()));

               Content attachedContent = contentRepository.findByContentId(contentTracker.getContent().getContentId())
                       .orElseThrow(() -> new ContentNotFoundException("Content with ID: " + request.getContentId() + " not found."));

                   Content detachedContent = new Content();
                   detachedContent.setContentBody(attachedContent.getContentBody());
                   Content savedContent = contentRepository.save(detachedContent);
                   contentTracker.setContent(savedContent);
                   contentTrackerRepository.save(contentTracker);
        }
        else {
            throw new ContentTrackerNotFoundException("No content trackers found for location id: " + request.getLocationId());
        }
    }
}
