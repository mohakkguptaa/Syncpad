package com.syncpad.syncpadservice.controller;

import com.syncpad.syncpadservice.dto.requests.CreateContentRequest;
import com.syncpad.syncpadservice.dto.requests.AttachContentRequest;
import com.syncpad.syncpadservice.dto.requests.DetachContentRequest;
import com.syncpad.syncpadservice.dto.requests.UpdateContentRequest;
import com.syncpad.syncpadservice.dto.responses.CreateContentResponse;
import com.syncpad.syncpadservice.dto.responses.ContentForLocation;
import com.syncpad.syncpadservice.repository.ContentTrackerRepository;
import com.syncpad.syncpadservice.service.ContentService;
import com.syncpad.syncpadservice.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ContentTrackerRepository contentTrackerRepository;

    @PostMapping("/create")
    public ResponseEntity<CreateContentResponse> createContent(@RequestBody CreateContentRequest dto){
        log.info("Content creation request received.");
        CreateContentResponse createContentResponse = contentService.createContent(dto);
        log.info("New content created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(createContentResponse);
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncContent(@RequestBody AttachContentRequest dto){
        log.info("Request received for syncing content");
        contentService.attachContentToLocation(dto);
        log.info("Content synced successfully.");
        return ResponseEntity.ok("Content synced successfully.");
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateContent(@RequestBody UpdateContentRequest dto){
        log.info("Request received for updating content");
        contentService.updateContent(dto);
        log.info("Content updated successfully.");
        return ResponseEntity.ok("Content updated successfully.");
    }

    @PostMapping("/unsync")
    public ResponseEntity<String> unsyncContent(@RequestBody DetachContentRequest dto){
        log.info("Request received for unsyncing content");
        contentService.detachContentFromLocation(dto);
        log.info("Content unsynced successfully.");
        return ResponseEntity.ok("Content unsync successfully.");
    }
}
