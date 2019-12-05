package com.drimmi.rtb;


import org.junit.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HTTPRequestExecutorTest {

    private final ConfigurationSupport configurationSupport = new ConfigurationSupport();

    @Test
    public void executeTest() throws IOException {
        EmitterConfiguration config = configurationSupport.buildConfiguration(3);

        HTTPRequestExecutor requestExecutor = new HTTPRequestExecutor(config);

        var generator = new RequestGenerator(config);
        RTBRequest request = generator.generate();

        var client = mock(HttpClient.class);

        requestExecutor.client = client;

        var httpRequest = mock(HttpRequest.class);
        var httpResponse = mock(HttpResponse.class);
        when(httpResponse.statusCode())
                .thenReturn(200)
                .thenReturn(500);

        var cf = new CompletableFuture<>();
        cf.completeExceptionally(new RuntimeException());

        when(client.sendAsync(any(HttpRequest.class), any()))
                .thenReturn(CompletableFuture.completedFuture(httpResponse))
                .thenReturn(CompletableFuture.completedFuture(httpResponse))
                .thenReturn(cf);

        requestExecutor.execute(request);

        assertEquals(1, requestExecutor.getNumOfSuccess());
        assertEquals(2, requestExecutor.getNumOfError());

    }
}
