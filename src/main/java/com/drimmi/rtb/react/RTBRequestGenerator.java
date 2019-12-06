package com.drimmi.rtb.react;

import java.util.concurrent.*;
import java.util.concurrent.Flow.Subscriber;

import static java.util.concurrent.TimeUnit.*;

public class RTBRequestGenerator {

    private final RequestGenerator generator;

    private SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

    public RTBRequestGenerator(RequestGenerator generator) {
        this.generator = generator;
    }

    public void subscribe(Subscriber<String> subscriber) {
        publisher.subscribe(subscriber);
    }

    public void startPublish() {
        generator.generate().buildContentStream()
                .forEach(item -> {
            //publisher.offer(item, (subscriber, _item) -> { return false; });
            publisher.submit(item);

        });
        publisher.close();
    }

}
