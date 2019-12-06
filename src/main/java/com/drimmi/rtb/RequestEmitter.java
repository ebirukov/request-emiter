package com.drimmi.rtb;

import com.drimmi.rtb.react.HTTPWorker;
import com.drimmi.rtb.react.RTBRequestGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RequestEmitter {

    private final HTTPRequestExecutor executor;

    private final RTBRequestGenerator rtbRequestGenerator;


    RequestEmitter(HTTPRequestExecutor executor, RTBRequestGenerator rtbRequestGenerator) {
        this.executor = executor;
        this.rtbRequestGenerator = rtbRequestGenerator;
    }

    public CompletableFuture<Void> reactiveProcess() {
        return CompletableFuture.runAsync(() -> rtbRequestGenerator.startPublish());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var configuration = new ConfigurationParser(args).getConfiguration();
        var emitter = RequestEmitter.newBuilder()
                                    .setConfiguration(configuration)
                                    .build();

        emitter.reactiveProcess().join();
        //TimeUnit.SECONDS.sleep(2);
    }

    public static RequestEmitterBuilder newBuilder() {
        return new RequestEmitterBuilderImpl();
    }

    interface RequestEmitterBuilder {

        RequestEmitter build();

        RequestEmitterBuilder setConfiguration(EmitterConfiguration configuration);
    }
}
