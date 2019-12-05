package com.drimmi.rtb;

import com.drimmi.rtb.react.HTTPWorker;
import com.drimmi.rtb.react.RTBRequestGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RequestEmitter {

    RequestGenerator generator;

    EmitterConfiguration configuration;

    HTTPRequestExecutor executor ;

    RTBRequestGenerator rtbRequestGenerator;


    RequestEmitter(RequestGenerator generator, EmitterConfiguration configuration, HTTPRequestExecutor executor, RTBRequestGenerator rtbRequestGenerator) {
        this.generator = generator;
        this.configuration = configuration;
        this.executor = executor;
        this.rtbRequestGenerator = rtbRequestGenerator;
    }

    public ProcessResult reactiveProcess() {
        rtbRequestGenerator.startPublish();
        return executor.getResult();
    }

    public ProcessResult processRequests() {
        var processResult = new ProcessResult();
        for (int i = 0; i < configuration.getBatchSize(); i++) {
            var rtbRequest = generator.generate();
            executor.execute(rtbRequest);
            System.out.println(executor.getResult());
        }
        return processResult;
    }

    public static void main(String[] args) {
        var configuration = new ConfigurationParser(args).getConfiguration();
        var emitter = RequestEmitter.newBuilder()
                                    .setConfiguration(configuration)
                                    .build();

        CompletableFuture.supplyAsync(emitter::reactiveProcess)
                .whenComplete(RequestEmitter::result)
                .join();
    }

    private static void result(ProcessResult processResult, Throwable throwable) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(processResult);
    }

    public static RequestEmitterBuilder newBuilder() {
        return new RequestEmitterBuilderImpl();
    }

    interface RequestEmitterBuilder {

        RequestEmitter build();

        RequestEmitterBuilder setConfiguration(EmitterConfiguration configuration);
    }
}
