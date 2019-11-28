package com.drimmi.rtb;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RTBRequestEmitterTest {



    private final ConfigurationSupport configurationSupport = new ConfigurationSupport();

    @Test
    public void processRequestsTest() {
        Processor processor = mock(Processor.class);

        EmitterConfiguration configuration = mock(EmitterConfiguration.class);

        RequestExecutor requestExecutor = mock(RequestExecutor.class);

        RTBRequestEmitter requestProcessor = new RTBRequestEmitter(processor, configuration, requestExecutor);

        when(processor.sendRequest()).thenReturn(false);
        EmitterConfiguration config = configurationSupport.buildConfiguration(1);
        var result = requestProcessor.processRequests();

        assertTrue(result != null);


    }

}
