package com.syncpad.syncpadservice.controller;

import com.syncpad.syncpadservice.dto.responses.ContentForLocation;
import com.syncpad.syncpadservice.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/{locationId}")
    public ResponseEntity<List<ContentForLocation>> getAllContentForLocation(@PathVariable Long id){
        log.info("Request received for fetching all contents for a location.");
        List<ContentForLocation> contentForLocation = locationService.getContentListForLocation(id);
        return ResponseEntity.ok(contentForLocation);
    }

    @GetMapping("{locationId}/content/{contentId}")
    public ResponseEntity<ContentForLocation> getContentForLocation(
            @PathVariable Long locationId, @PathVariable Long contentId) {
        log.info("Request received for fetching content with a Id for the location.");
        ContentForLocation contentForLocation = locationService.getContentForLocationById(locationId, contentId);
        return ResponseEntity.ok(contentForLocation);
    }
}
