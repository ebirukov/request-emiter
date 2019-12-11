package com.drimmi.rtb;

import com.drimmi.rtb.react.HTTPWorker;
import com.drimmi.rtb.react.DataProducer;
import com.drimmi.rtb.react.RTBRequestGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.*;

public class RequestEmitter {

    private final DataProducer dataProducer;

    private final HTTPWorker worker;

    private final HTTPRequestExecutor executor;

    private RequestEmitter(RequestEmitterBuilder builder) {
        this.dataProducer = builder.dataProducer;
        this.worker = builder.worker;
        this.executor = builder.executor;
    }

    public void reactiveProcess() {
        worker.addListener(() -> {
            System.out.println("success " + executor.getTotalNumOfSuccess() + " error " + executor.getTotalNumOfError());
        });
        CompletableFuture.runAsync(() -> dataProducer.startPublish()).join();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var configuration = new ConfigurationParser(args).getConfiguration();
        var emitter = RequestEmitter.newBuilder()
                                    .setConfiguration(configuration)
                                    .build();

        emitter.reactiveProcess();
    }

    public static RequestEmitterBuilder newBuilder() {
        return new RequestEmitterBuilder();
    }

    interface Builder<T> {

        T build();
    }

    static class RequestEmitterBuilder implements Builder<RequestEmitter> {

        private EmitterConfiguration configuration;

        private HTTPRequestExecutor executor;

        private DataProducer dataProducer;

        private HTTPWorker worker;

        @Override
        public RequestEmitter build() {
            requireNonNull(configuration, "setup configuration");
            setExecutor( requireNonNullElseGet(executor, this::createDefaultExecutor) );
            setDataProducer( requireNonNullElseGet(dataProducer, this::createDefaultGenerator) );

            worker = new HTTPWorker(executor, configuration.getNumOfRequests(), configuration.getBatchSize());
            dataProducer.subscribe(worker);
            return new RequestEmitter(this);
        }

        private DataProducer createDefaultGenerator() {
            var generator = new RTBRequestGenerator(configuration);
            return new DataProducer(generator);
        }

        private HTTPRequestExecutor createDefaultExecutor() {
            return new HTTPRequestExecutor(configuration);
        }

        public RequestEmitterBuilder setExecutor(HTTPRequestExecutor executor) {
            this.executor = executor;
            return this;
        }

        public RequestEmitterBuilder setDataProducer(DataProducer dataProducer) {
            this.dataProducer = dataProducer;
            return this;
        }

        public RequestEmitterBuilder setConfiguration(EmitterConfiguration configuration) {
            this.configuration = configuration;
            return this;
        }

    }
}
