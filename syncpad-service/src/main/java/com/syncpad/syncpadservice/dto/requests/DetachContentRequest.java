package com.syncpad.syncpadservice.dto.requests;

import lombok.Data;

@Data
public class DetachContentRequest {
    private Long locationId;

    private Long contentId;
}
