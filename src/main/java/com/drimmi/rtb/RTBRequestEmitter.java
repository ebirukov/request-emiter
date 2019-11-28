package com.drimmi.rtb;

public class RTBRequestEmitter {

    Processor processor;

    EmitterConfiguration configuration;

    RequestExecutor executor ;


    public RTBRequestEmitter(Processor processor, EmitterConfiguration configuration, RequestExecutor executor) {
        this.processor = processor;
        this.configuration = configuration;
        this.executor = executor;
    }

    public ProcessResult processRequests() {
        var generator = new RequestGenerator(configuration);
        var processResult = new ProcessResult();
        for (int i = 0; i < configuration.getBatchSize(); i++) {
            var rtbRequest = generator.generate();
            processResult = executor.execute(rtbRequest);
            System.out.println(processResult);
        }
        return processResult;
    }

    public static void main(String[] args) {
        var configuration = new ConfigurationParser(args).getConfiguration();
        var executor = new RequestExecutor(configuration);
        var emitter = new RTBRequestEmitter(new SequenceProcessor(), configuration, executor);
        System.out.println(emitter.processRequests());

    }


}
