package com.syncpad.syncpadservice.repository;

import com.syncpad.syncpadservice.entity.ContentTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface ContentTrackerRepository extends JpaRepository<ContentTracker, Long> {

    List<ContentTracker> findByLocationId(Long locationId);

    Optional<ContentTracker> findByLocationIdAndContent_ContentId(Long locationId, Long contentId);
}
