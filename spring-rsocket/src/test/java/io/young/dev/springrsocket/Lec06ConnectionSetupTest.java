package io.young.dev.springrsocket;

import io.rsocket.transport.netty.client.TcpClientTransport;
import io.young.dev.springrsocket.dto.ClientConnectionRequest;
import io.young.dev.springrsocket.dto.ComputationRequestDto;
import io.young.dev.springrsocket.dto.ComputationResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest(properties = {"spring.rsocket.server.port=7000"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec06ConnectionSetupTest {

    private RSocketRequester requester;

    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setup() {
        ClientConnectionRequest request = new ClientConnectionRequest();
        request.setClientId("order-service");
        request.setSecretKey("password");

        this.requester = this.builder
                .setupData(request)
                .transport(TcpClientTransport.create("localhost", 6565));
    }

    @RepeatedTest(3)
    public void connectionTest() {
        Mono<ComputationResponseDto> mono = this.requester.route("math.service.square")
                .data(new ComputationRequestDto(ThreadLocalRandom.current().nextInt(1, 50)))
                .retrieveMono(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }
}
