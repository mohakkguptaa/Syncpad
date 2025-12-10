package com.syncpad.syncpadservice.service;

import com.syncpad.syncpadservice.dto.responses.ContentForLocation;
import com.syncpad.syncpadservice.entity.Content;
import com.syncpad.syncpadservice.entity.ContentTracker;
import com.syncpad.syncpadservice.exceptions.ContentNotFoundException;
import com.syncpad.syncpadservice.exceptions.ContentTrackerNotFoundException;
import com.syncpad.syncpadservice.exceptions.LocationHasNoContentException;
import com.syncpad.syncpadservice.exceptions.NoRecordFoundException;
import com.syncpad.syncpadservice.repository.ContentRepository;
import com.syncpad.syncpadservice.repository.ContentTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    private ContentTrackerRepository contentTrackerRepository;

    @Autowired
    private ContentRepository contentRepository;

    public List<ContentForLocation> getContentListForLocation(Long id){
        List<ContentTracker> contentList = contentTrackerRepository.findByLocationId(id);
        if(contentList.isEmpty()) {
            throw new ContentTrackerNotFoundException("No content trackers found for location id: " + id);
        }

        List<Long> contentIds = contentList.stream()
                    .map(cl -> cl.getContent().getContentId())
                    .toList();

        if(contentIds.isEmpty()) {
            throw new LocationHasNoContentException("No content id has been found for a location with id: " + id);
        }

        List<Content> contents = contentRepository.findAllByContentIdIn(contentIds);

        if(contents.isEmpty()) {
            throw new ContentNotFoundException("No content found");
        }

        return contents.stream()
                .map(c -> {
                    ContentForLocation contentForLocation = new ContentForLocation();
                    contentForLocation.setContentBody(c.getContentBody());
                    contentForLocation.setVersion(c.getVersion());
                    return contentForLocation;
                }).toList();
    }

    public ContentForLocation getContentForLocationById(Long locationId, Long contentId){
        ContentTracker contentTracker = contentTrackerRepository.findByLocationIdAndContent_ContentId(locationId, contentId)
                .orElseThrow(() -> new NoRecordFoundException("No record found with Location id: " + locationId + " and content id: " + contentId));

            ContentForLocation contentForLocation = new ContentForLocation();
            contentForLocation.setContentBody(contentTracker.getContent().getContentBody());
            contentForLocation.setVersion(contentTracker.getContent().getVersion());

            return contentForLocation;
    }
}
