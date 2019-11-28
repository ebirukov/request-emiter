package com.drimmi.rtb;


import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RequestExecutorTest {

    private final ConfigurationSupport configurationSupport = new ConfigurationSupport();

    @Test
    public void executeTest() throws IOException {
        EmitterConfiguration config = configurationSupport.buildConfiguration(3);

        RequestExecutor requestExecutor = new RequestExecutor(config);

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

        ProcessResult processResult = requestExecutor.execute(request);

        assertNotNull(processResult);
        assertEquals(1, processResult.getNumOfSuccess());
        assertEquals(1, processResult.getNumOfError());
        assertEquals(1, processResult.getNumOfFailed());

    }
}
