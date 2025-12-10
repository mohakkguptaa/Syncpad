package com.syncpad.syncpadservice.repository;

import com.syncpad.syncpadservice.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    Optional<Content> findByContentId(Long contentId);

    List<Content> findAllByContentIdIn(List<Long> contentIds);
}
