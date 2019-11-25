package com.drimmi.rtb;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RequestExecutorTest {

    RequestExecutor requestExecutor = new RequestExecutor();

    @Test
    public void executeTest() throws IOException {
        RTBRequest request = new RTBRequest();
        assertEquals(200, requestExecutor.execute(request));
    }
}
