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

    RequestExecutor executor ;


    public RTBRequestEmitter(RequestGenerator generator, EmitterConfiguration configuration, RequestExecutor executor) {
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
            processResult = executor.execute(rtbRequest);
            System.out.println(processResult);
        }
        return processResult;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        var configuration = new ConfigurationParser(args).getConfiguration();
        var executor = new RequestExecutor(configuration);
        RequestGenerator generator = new RequestGenerator(configuration);

        var emitter = new RTBRequestEmitter(generator, configuration, executor);
        CompletableFuture.supplyAsync(emitter::reactiveProcess)
                .whenComplete(RTBRequestEmitter::result)
                .join();
        //System.out.println(emitter.processRequests());

    }

    private static void result(ProcessResult processResult, Throwable throwable) {
        System.out.println(processResult);
    }
}
