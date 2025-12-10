package com.syncpad.syncpadservice.dto.requests;

import lombok.Data;

@Data
public class UpdateContentRequest {
    private String contentBody;

    private Long contentId;
}
