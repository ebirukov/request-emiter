package com.drimmi.rtb;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RequestEmitterTest {



    private final ConfigurationSupport configurationSupport = new ConfigurationSupport();

    @Test
    public void processRequestsTest() {
        Processor processor = mock(Processor.class);

        EmitterConfiguration configuration = mock(EmitterConfiguration.class);

        HTTPRequestExecutor requestExecutor = mock(HTTPRequestExecutor.class);

        RequestGenerator generator = mock(RequestGenerator.class);

        EmitterConfiguration config = configurationSupport.buildConfiguration(1);
        RequestEmitter.RequestEmitterBuilder builder = RequestEmitter.newBuilder().setConfiguration(config);

        when(processor.sendRequest()).thenReturn(false);

        var requestProcessor = builder.build();

        var result = requestProcessor.processRequests();

        assertTrue(result != null);


    }

}
