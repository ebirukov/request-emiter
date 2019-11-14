package com.drimmi.rtb;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RTBRequestProcessorTest {

    ISenderService senderService = mock(ISenderService.class);

    RTBRequestProcessor requestProcessor = new RTBRequestProcessor(senderService);

    @Test
    public void processRequestsTest() {

        when(senderService.sendRequest()).thenReturn(false);

        ProcessResult result = requestProcessor.processRequests(10);

        assertTrue(result != null);
        assertEquals(10, result.getNumOfFailed());
        assertEquals(0, result.getNumOfSuccess());


        when(senderService.sendRequest()).thenReturn(true);

        result = requestProcessor.processRequests(5);

        assertTrue(result != null);
        assertEquals(0, result.getNumOfFailed());
        assertEquals(5, result.getNumOfSuccess());




    }

}
