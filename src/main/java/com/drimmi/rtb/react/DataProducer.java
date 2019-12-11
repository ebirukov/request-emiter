package com.drimmi.rtb.react;

import java.util.concurrent.*;
import java.util.concurrent.Flow.Subscriber;

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
        generator.start(publisher::submit);
        publisher.close();
    }

}
