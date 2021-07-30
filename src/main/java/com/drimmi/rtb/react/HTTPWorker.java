package com.drimmi.rtb.react;

import com.drimmi.rtb.HTTPRequestExecutor;
import com.drimmi.rtb.JobResult;
import com.drimmi.rtb.HTTPRequestExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.function.Predicate;

import static java.util.concurrent.Flow.*;

public class HTTPWorker implements Subscriber<String> {

    private int batchSize;

    private Subscription subscription;

    private Collection<CompletableFuture> jobFuture;

    private volatile boolean complete = false;

    private final HTTPRequestExecutor executor;

    private final BatchOptimizer batchOptimizer;

    private int unprocessedCountDown;

    private final Collection<WorkerListener> listeners = new ArrayList<>();

    public HTTPWorker(HTTPRequestExecutor executor, int numOfRequests, int batchSize) {
        this.executor = executor;
        this.batchOptimizer = executor instanceof JobResult ? new BatchOptimizer(executor) : null;
        this.batchSize = batchSize;
        this.unprocessedCountDown = numOfRequests;
    }

    public void addListener(WorkerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        Executors.newSingleThreadExecutor().submit(this::watchBatchJobsDone);
        requestData(false);
    }

    private void watchBatchJobsDone() {
        while (!complete || hasUncompletedJobs()) {
            Thread.onSpinWait();
            if (jobFuture.size() < batchSize) continue;
            CompletableFuture.allOf(jobFuture.toArray(CompletableFuture[]::new))
                            .whenComplete((r, e) -> requestData(true))
                            .join();
        }
        listeners.forEach(WorkerListener::onComplete);
    }

    private boolean hasUncompletedJobs() {
        return jobFuture.stream().anyMatch(Predicate.not(CompletableFuture::isDone));
    }

    private void requestData(boolean needOptimize) {
        if (batchOptimizer != null && needOptimize) {
            batchSize = Math.min(batchOptimizer.nextBatch(batchSize), unprocessedCountDown);
        }
        jobFuture = new ArrayList<>(batchSize);
        subscription.request(batchSize);
        unprocessedCountDown -= batchSize;
    }

    @Override
    public void onNext(String item) {
        CompletableFuture future = executor.send(item);
        if (jobFuture.size() < batchSize) {
            jobFuture.add(future);
        } else {
            throw new RuntimeException("unexpected item: " + item);
        }
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        complete = true;
    }

}
