package com.drimmi.rtb.react;

import com.drimmi.rtb.HTTPRequestExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.concurrent.Flow.*;

public class HTTPWorker implements Subscriber<String> {

    private int numOfBatch;

    private Subscription subscription;

    private Collection<CompletableFuture> jobFuture;

    private boolean complete = false;

    private final HTTPRequestExecutor executor;

    private int unprocessedCountDown;

    public HTTPWorker(HTTPRequestExecutor executor, int numOfRequests, int numOfBatch) {
        this.executor = executor;
        this.numOfBatch = numOfBatch;
        this.unprocessedCountDown = numOfRequests;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        Executors.newSingleThreadExecutor().submit(this::watchBatchJobsDone);
        requestData();
    }

    private void watchBatchJobsDone() {
        while (!complete || hasUncompletedJobs()) {
            Thread.onSpinWait();
            if (jobFuture.size() < numOfBatch) continue;
            CompletableFuture.allOf(jobFuture.toArray(CompletableFuture[]::new))
                    .whenComplete(this::requestNextTasks)
                    .join();
        }
        System.out.println("success " + executor.getTotalNumOfSuccess() + " error " + executor.getTotalNumOfError());
    }

    private void requestNextTasks(Void aVoid, Throwable throwable) {
        if (throwable != null) {
            throwable.printStackTrace();
        }
        numOfBatch = calcNextBatch();
        if (executor.getNumOfError() > 0) System.out.println(" numOfError " + executor.getNumOfError() + ": next numOfBatch " + numOfBatch);
        executor.clear();
        requestData();
    }

    private boolean hasUncompletedJobs() {
        return jobFuture.stream().anyMatch(Predicate.not(CompletableFuture::isDone));
    }

    private int calcNextBatch() {
        if (executor.getNumOfError() > 0) {
            numOfBatch -= (numOfBatch / 2);
        } else {
            numOfBatch += (numOfBatch / 2);
        }
        return Math.min(numOfBatch < 1 ? 1 : numOfBatch, unprocessedCountDown);
    }

    private void requestData() {
        jobFuture = new ArrayList<>(numOfBatch);
        subscription.request(numOfBatch);
        unprocessedCountDown -= numOfBatch;
    }

    @Override
    public void onNext(String item) {
        CompletableFuture future = executor.send(item);
        if (jobFuture.size() < numOfBatch) {
            jobFuture.add(future);
        } else {
            System.err.println("WTF!");
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        complete = true;
    }
}
