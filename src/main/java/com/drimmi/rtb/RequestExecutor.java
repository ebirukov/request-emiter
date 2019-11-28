package com.drimmi.rtb;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RequestExecutor {

    HttpClient client;

    ProcessResult result;

    HttpRequest.Builder httpRequestBuilder;

    CompletableFuture[] futures;

    public RequestExecutor(EmitterConfiguration config) {
        this.result = new ProcessResult();
        client = HttpClient.newBuilder()
                //.executor(Executors.newFixedThreadPool(config.getNumOfParallelWorker()))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        this.httpRequestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(config.getUrl()))
                .timeout(Duration.ofMillis(config.getRequestTimeout()));
    }

    public ProcessResult execute(RTBRequest rtbRequest) {
        CompletableFuture.allOf(
            rtbRequest.buildContentStream()
                    .map(s -> this.submitJob(s))
                    .toArray(CompletableFuture[]::new)
        ).join();
        return result;
    }

    private CompletableFuture submitJob(String body) {
        HttpRequest httpRequest = httpRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client
                .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .thenAccept(this::accumulateResult)
                .exceptionally(this::onError);
    }

    private Void onError(Throwable throwable) {
        result.incrementFailed();
        return null;
    }

    private void accumulateResult(int s) {
        if (s < 300 && s >= 200) {
            result.incrementSuccess();
        } else {
            result.incrementError();
        }
    }
}
