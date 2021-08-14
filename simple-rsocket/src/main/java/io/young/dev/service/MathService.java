package io.young.dev.service;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.young.dev.dto.RequestDto;
import io.young.dev.dto.ResponseDto;
import io.young.dev.util.ObjectUtil;
import reactor.core.publisher.Mono;

public class MathService implements RSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        System.out.println("Receiving: " + ObjectUtil.toObject(payload, RequestDto.class));
        return Mono.empty();
    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        return Mono.fromSupplier(() -> {
            RequestDto requestDto = ObjectUtil.toObject(payload, RequestDto.class);
            ResponseDto responseDto = new ResponseDto(
                    requestDto.getInput(),
                    requestDto.getInput() * requestDto.getInput()
            );
            return ObjectUtil.toPayload(responseDto);
        });
    }
}
