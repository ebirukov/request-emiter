package com.drimmi.rtb;

import com.drimmi.rtb.react.HTTPWorker;
import com.drimmi.rtb.react.RTBRequestGenerator;
import com.drimmi.rtb.react.RequestGenerator;


class RequestEmitterBuilderImpl implements RequestEmitter.RequestEmitterBuilder {

    private EmitterConfiguration configuration;

    @Override
    public RequestEmitter build() {
        var executor = new HTTPRequestExecutor(configuration);
        RequestGenerator generator = new RequestGenerator(configuration);
        HTTPWorker worker = new HTTPWorker(executor, configuration.getNumOfRequests(), configuration.getBatchSize());
        RTBRequestGenerator requestGenerator = new RTBRequestGenerator(generator);

        requestGenerator.subscribe(worker);

        return new RequestEmitter(executor, requestGenerator);
    }

    @Override
    public RequestEmitter.RequestEmitterBuilder setConfiguration(EmitterConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

}
