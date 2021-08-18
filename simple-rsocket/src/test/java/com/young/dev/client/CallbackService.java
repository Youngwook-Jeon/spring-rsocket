package com.young.dev.client;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.young.dev.dto.ResponseDto;
import io.young.dev.util.ObjectUtil;
import reactor.core.publisher.Mono;

public class CallbackService implements RSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        System.out.println("Client received: " + ObjectUtil.toObject(payload, ResponseDto.class));
        return Mono.empty();
    }
}
