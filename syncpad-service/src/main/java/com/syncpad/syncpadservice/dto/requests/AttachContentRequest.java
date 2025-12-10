package com.syncpad.syncpadservice.dto.requests;

import lombok.Data;

@Data
public class AttachContentRequest {
    private Long contentId;

    private Long locationId;
}
