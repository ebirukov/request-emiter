package com.drimmi.rtb.react;

import com.drimmi.rtb.RequestGenerator;

import java.util.concurrent.*;
import java.util.concurrent.Flow.Subscriber;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class RTBRequestGenerator {

    private final RequestGenerator generator;

    private SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

    public RTBRequestGenerator(RequestGenerator generator) {
        this.generator = generator;
    }

    public void subscribe(Subscriber<String> subscriber) {

        publisher.subscribe(subscriber);
/*
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println(publisher.estimateMaximumLag());
        }, 50, 40, TimeUnit.MILLISECONDS);*/

    }

    public void startPublish() {
        generator.generate().buildContentStream().forEach(item -> {
            //publisher.offer(item, this::drop);
            publisher.submit(item);

        });
        publisher.close();
    }

    private boolean drop(Subscriber<? super String> subscriber, String s) {
        System.out.println(s);
        return false;
    }

}
