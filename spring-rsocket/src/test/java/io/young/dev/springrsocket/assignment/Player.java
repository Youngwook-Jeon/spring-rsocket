package io.young.dev.springrsocket.assignment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;

public class Player {

    private final Sinks.Many<Integer> sinks = Sinks.many().unicast().onBackpressureBuffer();
    private int lower = 0;
    private int upper = 100;
    private int mid = 0;
    private int attempts = 0;

    public Flux<Integer> guesses() {
        return this.sinks.asFlux();
    }

    public void play() {
        this.emit();
    }

    public Consumer<GuessNumberResponse> receives() {
        return this::processResponse;
    }

    private void processResponse(GuessNumberResponse numberResponse) {
        attempts++;
        System.out.println(attempts + " : " + mid + " : " + numberResponse);
        if (GuessNumberResponse.EQUAL.equals(numberResponse)) {
            this.sinks.tryEmitComplete();
            return;
        }

        if (GuessNumberResponse.GREATER.equals(numberResponse))
            lower = mid;
        else if (GuessNumberResponse.LESSER.equals(numberResponse))
            upper = mid;
        this.emit();
    }

    private void emit() {
        mid = lower + (upper - lower) / 2;
        this.sinks.tryEmitNext(mid);
    }
}
