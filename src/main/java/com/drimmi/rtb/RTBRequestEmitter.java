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
        var rtbRequest = new RequestGenerator().generate(configuration);
        return executor.execute(rtbRequest);

    }

    public static void main(String[] args) {
        EmitterConfiguration configuration = new ConfigurationParser(args).getConfiguration();
        var emitter = new RTBRequestEmitter(new SequenceProcessor(), configuration, new RequestExecutor(configuration));
        var processResult = emitter.processRequests();
    }
}
