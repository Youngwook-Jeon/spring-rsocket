package io.young.dev.springrsocket;

import io.rsocket.transport.netty.client.TcpClientTransport;
import io.young.dev.springrsocket.dto.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(properties = {"spring.rsocket.server.port=7000"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec03InputValidationTest {

    private RSocketRequester requester;

    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setup() {
        this.requester = this.builder.transport(TcpClientTransport.create("localhost", 6565));
    }

    @Test
    public void validationTest() {
        Mono<Integer> mono = this.requester.route("math.validation.double.31")
                .retrieveMono(Integer.class)
                .onErrorReturn(Integer.MIN_VALUE)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void responseTest() {
        Mono<Response<Integer>> mono = this.requester.route("math.validation.double.response.31")
                .retrieveMono(new ParameterizedTypeReference<Response<Integer>>() {
                })
                .doOnNext(r -> {
                    if (r.hasError())
                        System.out.println(r.getErrorResponse().getStatusCode().getDescription());
                    else
                        System.out.println(r.getSuccessResponse());
                });

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }
}
