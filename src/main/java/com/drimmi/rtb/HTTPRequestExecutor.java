package com.drimmi.rtb;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class HTTPRequestExecutor implements JobResult {

    HttpClient client;

    ProcessResult result;

    HttpRequest.Builder httpRequestBuilder;

    CompletableFuture[] futures;

    public HTTPRequestExecutor(EmitterConfiguration config) {
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

    public void execute(RTBRequest rtbRequest) {
        CompletableFuture.allOf(
            rtbRequest.buildContentStream()
                    .map(s -> this.send(s))
                    .toArray(CompletableFuture[]::new)
        ).join();
    }

    public CompletableFuture<Integer> send(String body) {
        HttpRequest httpRequest = httpRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client
                .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .exceptionally(this::onError)
                .whenComplete(this::accumulateResult)
                ;
    }

    private int onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return 0;
    }

    private void accumulateResult(int s, Throwable throwable) {
        if (throwable != null || s == 0) {
            result.incrementError();
        } else if (s < 300 && s >= 200) {
            result.incrementSuccess();
        } else {
            result.incrementError();
        }
    }

    public ProcessResult getResult() {
        return result;
    }

    @Override
    public int getNumOfError() {
        return result.getNumOfError();
    }

    @Override
    public int getNumOfSuccess() {
        return result.getNumOfSuccess();
    }
}
