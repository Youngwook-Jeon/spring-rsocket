package io.young.dev.springrsocket.dto;

import lombok.Data;

@Data
public class ClientConnectionRequest {

    private String clientId;
    private String secretKey;
}
