package com.drimmi.rtb;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RTBRequestProcessorTest {

    @Test
    public void processRequestsTest() {
        int numOfRequests = 10;
        ISenderService senderService = mock(ISenderService.class);
        ProcessResult result = new ProcessResult(numOfRequests, 0);
        when(senderService.sendRequests(numOfRequests)).thenReturn(result);
        assertEquals(0, senderService.sendRequests(numOfRequests).getNumOfFailed() );
        assertEquals(10, senderService.sendRequests(numOfRequests).getNumOfSuccess() );
    }

}
