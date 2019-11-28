package com.drimmi.rtb;

import org.mockito.Mockito;

public class ConfigurationSupport {
    public ConfigurationSupport() {
    }

    public EmitterConfiguration buildConfiguration(int requestCount) {
        var config = Mockito.mock(EmitterConfiguration.class);

        Mockito.when(config.getNumOfParallelWorker()).thenReturn(EmitterConfiguration.DEFAULT_WORKERS);
        Mockito.when(config.getUrl()).thenReturn(EmitterConfiguration.DEFAULT_URL);
        Mockito.when(config.getNumOfRequests()).thenReturn(requestCount);
        Mockito.when(config.getRequestTimeout()).thenReturn(EmitterConfiguration.DEFAULT_TIMEOUT);
        return config;
    }
}