package io.young.dev.springrsocket;

import io.rsocket.transport.netty.client.TcpClientTransport;
import io.young.dev.springrsocket.assignment.GuessNumberResponse;
import io.young.dev.springrsocket.assignment.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest(properties = {"spring.rsocket.server.port=7000"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec05AssignmentTest {

    private RSocketRequester requester;

    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setup() {
        this.requester = this.builder
                .transport(TcpClientTransport.create("localhost", 6565));
    }

    @Test
    public void assignment() {
        Player player = new Player();
        Mono<Void> mono = this.requester.route("guess.a.number")
                .data(player.guesses().delayElements(Duration.ofSeconds(1)))
                .retrieveFlux(GuessNumberResponse.class)
                .doOnNext(player.receives())
                .doFirst(player::play)
                .then();

        StepVerifier.create(mono)
                .verifyComplete();
    }
}
