package com.drimmi.rtb;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RTBRequestEmitterTest {

    Processor processor = mock(Processor.class);

    RTBRequestEmitter requestProcessor = new RTBRequestEmitter(processor);

    @Test
    public void processRequestsTest() {

        when(processor.sendRequest()).thenReturn(false);

        ProcessResult result = requestProcessor.processRequests(10);

        assertTrue(result != null);
        assertEquals(10, result.getNumOfFailed());
        assertEquals(0, result.getNumOfSuccess());


        when(processor.sendRequest()).thenReturn(true);

        result = requestProcessor.processRequests(5);

        assertTrue(result != null);
        assertEquals(0, result.getNumOfFailed());
        assertEquals(5, result.getNumOfSuccess());




    }

}
