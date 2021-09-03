package io.young.dev.springrsocket.service;

import io.young.dev.springrsocket.dto.ChartResponseDto;
import io.young.dev.springrsocket.dto.ComputationRequestDto;
import io.young.dev.springrsocket.dto.ComputationResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class MathService {

    public Mono<Void> print(Mono<ComputationRequestDto> requestDtoMono) {
        return requestDtoMono
                .doOnNext(System.out::println)
                .then();
    }

    public Mono<ComputationResponseDto> findSquare(Mono<ComputationRequestDto> requestDtoMono) {
        return requestDtoMono
                .map(ComputationRequestDto::getInput)
                .map(i -> new ComputationResponseDto(i, i * i));
    }

    public Flux<ComputationResponseDto> tableStream(ComputationRequestDto dto) {
        return Flux.range(1, 1000)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> new ComputationResponseDto(dto.getInput(), dto.getInput() * i));
    }

    public Flux<ChartResponseDto> chartStream(Flux<ComputationRequestDto> dto) {
        return dto.map(ComputationRequestDto::getInput)
                .map(i -> new ChartResponseDto(i, (i * i) + 1));
    }
}
