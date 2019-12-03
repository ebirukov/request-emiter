package com.drimmi.rtb.react;

import com.drimmi.rtb.RequestGenerator;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

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
        generator.generate().buildContentStream().forEach(item -> {
            //publisher.offer(item, 100, TimeUnit.MILLISECONDS, this::drop);
            publisher.submit(item);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        publisher.close();
    }

    private boolean drop(Subscriber<? super String> subscriber, String s) {
        System.out.println(subscriber);
        return false;
    }

}
