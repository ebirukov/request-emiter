package com.drimmi.rtb.react;

import com.drimmi.rtb.HTTPRequestExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.concurrent.Flow.*;

public class HTTPWorker implements Subscriber<String> {

    private final int numOfRequests;

    int numOfBatch;

    Subscription subscription;

    AtomicInteger counter = new AtomicInteger(0);

    CompletableFuture[] futures;

    boolean complete = false;

    HTTPRequestExecutor executor;

    public HTTPWorker(HTTPRequestExecutor executor, int numOfRequests, int numOfBatch) {
        this.executor = executor;
        this.numOfRequests = numOfRequests;
        this.numOfBatch = numOfBatch;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        watchBatchDone();
        requestData(numOfBatch);
    }

    private void watchBatchDone() {
        Executors.newSingleThreadExecutor().submit(() -> {
            while (!complete) {
                Thread.onSpinWait();
                if (counter.getAcquire() < numOfBatch) continue;
                if (Stream.of(futures).filter(Predicate.not(CompletableFuture::isDone)).count() > 0) continue;
                counter.set(0);
                requestData(calcNextBatch());
                //System.out.println(result);
            }
        });
    }

    private int calcNextBatch() {
        var nextNumOfBatch = executor.getNumOfSuccess() > 0 ? executor.getNumOfSuccess() : 1;
        return nextNumOfBatch;
    }

    private void requestData(int numOfNextBatch) {
        numOfBatch = numOfNextBatch;
        System.out.println("numOfNextBatch " + numOfNextBatch);
        futures = new CompletableFuture[numOfNextBatch];
        subscription.request(numOfBatch);
    }

    @Override
    public void onNext(String item) {
        CompletableFuture future = executor.send(item);
        if (counter.getAcquire() < numOfBatch) {
            futures[counter.getAndIncrement()] = future;
        } else {
            System.err.println("WTF");
            counter.set(0);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        complete = true;
        System.out.println("complete");
        //System.out.println(result);
    }
}
