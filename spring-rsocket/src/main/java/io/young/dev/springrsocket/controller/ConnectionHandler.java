package io.young.dev.springrsocket.controller;

import io.young.dev.springrsocket.dto.ClientConnectionRequest;
import io.young.dev.springrsocket.service.MathClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ConnectionHandler {

    @Autowired
    private MathClientManager clientManager;

//    @ConnectMapping
//    public Mono<Void> handleConnection(ClientConnectionRequest request, RSocketRequester rSocketRequester) {
//        System.out.println("connection setup: " + request);
//        return request.getSecretKey().equals("password") ?
//                Mono.empty() :
//                Mono.fromRunnable(() -> rSocketRequester.rsocketClient().dispose());
//    }

    @ConnectMapping
    public Mono<Void> handleConnection(RSocketRequester rSocketRequester) {
        System.out.println("connection setup");
        return Mono.fromRunnable(() -> this.clientManager.add(rSocketRequester));
    }
}
