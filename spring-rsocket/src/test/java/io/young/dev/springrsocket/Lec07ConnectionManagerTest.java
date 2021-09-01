package io.young.dev.springrsocket;

import io.rsocket.transport.netty.client.TcpClientTransport;
import io.young.dev.springrsocket.dto.ComputationRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec07ConnectionManagerTest {

    @Autowired
    private RSocketRequester.Builder builder;

    @Test
    public void connectionTest() throws InterruptedException {
        RSocketRequester requester1 = this.builder
                .transport(TcpClientTransport.create("localhost", 6565));

        RSocketRequester requester2 = this.builder
                .transport(TcpClientTransport.create("localhost", 6565));

        requester1.route("math.service.print").data(new ComputationRequestDto(5)).send().subscribe();
        requester2.route("math.service.print").data(new ComputationRequestDto(5)).send().subscribe();

        Thread.sleep(10000);
    }
}
