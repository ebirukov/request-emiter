package com.drimmi.rtb.react;

import com.drimmi.rtb.RequestExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.concurrent.Flow.*;

public class HTTPWorker implements Subscriber<String> {

    RequestExecutor executor;

    int numOfBatch;

    Subscription subscription;

    AtomicInteger counter = new AtomicInteger(0);

    CompletableFuture[] futures;

    boolean complete = false;

    public HTTPWorker(RequestExecutor executor, int numOfBatch) {
        this.executor = executor;
        this.numOfBatch = numOfBatch;
        futures = new CompletableFuture[numOfBatch];
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        Executors.newSingleThreadExecutor().submit(() -> {
            while (!complete) {
                Thread.onSpinWait();
                if (counter.getAcquire() < numOfBatch) continue;
/*                Stream.of(futures).filter(Predicate.not(CompletableFuture::isDone)).forEach(
                        cf -> {
                            try {
                                System.out.println(cf.get());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                );*/
/*                try {
                    CompletableFuture.allOf(futures).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }*/
                requestData();
                counter.set(0);
            }
        });
        requestData();
    }

    private void requestData() {
        subscription.request(numOfBatch);
    }

    @Override
    public void onNext(String item) {
        CompletableFuture cf = executor.send(item);
        if (counter.get() < numOfBatch) {
            futures[counter.getAndIncrement()] = cf;
        } else {
            System.err.println("WTF");
        }
        System.out.println("request " + counter + " of batch " + numOfBatch);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        complete = true;
        System.out.println("complete");
    }
}
