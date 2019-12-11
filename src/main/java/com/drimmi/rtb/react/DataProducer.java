package com.drimmi.rtb.react;

import java.util.concurrent.*;
import java.util.concurrent.Flow.Subscriber;

import static java.util.concurrent.TimeUnit.*;

public class DataProducer {

    private final GenericDataGenerator<String> generator;

    private SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

    public DataProducer(GenericDataGenerator<String> generator) {
        this.generator = generator;
    }

    public void subscribe(Subscriber<String> subscriber) {
        publisher.subscribe(subscriber);
    }

    public void startPublish() {
        generator.start(item -> {
            //publisher.offer(item, (subscriber, _item) -> { return false; });
            publisher.submit(item);

        });
/*        generator.generate().buildContentStream()
                .forEach(item -> {
            //publisher.offer(item, (subscriber, _item) -> { return false; });
            publisher.submit(item);

        });*/
        publisher.close();
    }

}
