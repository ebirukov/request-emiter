package com.drimmi.rtb;

import com.drimmi.rtb.react.HTTPWorker;
import com.drimmi.rtb.react.RTBRequestGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RTBRequestEmitter {

    RequestGenerator generator;

    EmitterConfiguration configuration;

    HTTPRequestExecutor executor ;


    public RTBRequestEmitter(RequestGenerator generator, EmitterConfiguration configuration, HTTPRequestExecutor executor) {
        this.generator = generator;
        this.configuration = configuration;
        this.executor = executor;
    }

    public ProcessResult reactiveProcess() {
        HTTPWorker worker = new HTTPWorker(executor, configuration.getNumOfRequests(), configuration.getBatchSize());
        RTBRequestGenerator requestGenerator = new RTBRequestGenerator(generator);
        requestGenerator.subscribe(worker);
        requestGenerator.startPublish();
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

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        var configuration = new ConfigurationParser(args).getConfiguration();
        var executor = new HTTPRequestExecutor(configuration);
        RequestGenerator generator = new RequestGenerator(configuration);

        var emitter = new RTBRequestEmitter(generator, configuration, executor);
        CompletableFuture.supplyAsync(emitter::reactiveProcess)
                .whenComplete(RTBRequestEmitter::result)
                .join();
        //System.out.println(emitter.processRequests());
        TimeUnit.SECONDS.sleep(1);
        System.out.println(executor.getResult());
    }

    private static void result(ProcessResult processResult, Throwable throwable) {
        System.out.println(processResult);
    }
}
