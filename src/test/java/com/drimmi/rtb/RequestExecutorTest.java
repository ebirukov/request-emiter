package com.drimmi.rtb;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RequestExecutorTest {

    RequestExecutor requestExecutor = new RequestExecutor();

    @Test
    public void executeTest() {
        RTBRequest request = new RTBRequest();
        assertEquals(200, requestExecutor.execute(request));
    }
}
