package com.drimmi.rtb;

import org.mockito.Mockito;

public class ConfigurationSupport {
    public ConfigurationSupport() {
    }

    public EmitterConfiguration getConfiguration() {
        var config = Mockito.mock(EmitterConfiguration.class);

        Mockito.when(config.getNumOfParallelWorker()).thenReturn(EmitterConfiguration.DEFAULT_WORKERS);
        Mockito.when(config.getUrl()).thenReturn(EmitterConfiguration.DEFAULT_URL);
        Mockito.when(config.getNumOfRequests()).thenReturn(RequestExecutorTest.REQUEST_COUNT);
        Mockito.when(config.getRequestTimeout()).thenReturn(EmitterConfiguration.DEFAULT_TIMEOUT);
        return config;
    }
}