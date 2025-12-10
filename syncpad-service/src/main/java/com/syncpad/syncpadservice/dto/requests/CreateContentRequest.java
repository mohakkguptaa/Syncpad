package com.syncpad.syncpadservice.dto.requests;

import lombok.Data;

@Data
public class CreateContentRequest {
    private String contentBody;

    private Long locationId;
}
