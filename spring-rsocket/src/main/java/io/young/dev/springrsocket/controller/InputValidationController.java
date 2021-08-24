package io.young.dev.springrsocket.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("math.validation")
public class InputValidationController {

    @MessageMapping("double.{input}")
    public Mono<Integer> doubleIt(@DestinationVariable int input) {
        return Mono.just(input)
                .filter(i -> i < 31)
                .map(i -> i * 2)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("cannot > 30")));
    }

    @MessageExceptionHandler
    public Mono<Integer> handleException(Exception exception) {
        return Mono.just(-1);
    }
}
