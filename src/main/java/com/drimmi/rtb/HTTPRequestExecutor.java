package com.drimmi.rtb;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class HTTPRequestExecutor implements JobResult {

    private final HttpClient client;

    private final ProcessResult result;

    private final HttpRequest.Builder httpRequestBuilder;

    public HTTPRequestExecutor(String url, int requestTimeout) {
        this.result = new ProcessResult();
        client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        this.httpRequestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMillis(requestTimeout));
    }

    public CompletableFuture<Integer> send(String body) {
        HttpRequest httpRequest = httpRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client
                .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .exceptionally(this::onError)
                .whenComplete(this::accumulateResult);
    }

    private int onError(Throwable throwable) {
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

    @Override
    public int getNumOfError() {
        return result.getNumOfError();
    }

    @Override
    public int getNumOfSuccess() {
        return result.getNumOfSuccess();
    }

    @Override
    public int getTotalNumOfSuccess() {
        return result.getTotal().getNumOfSuccess();
    }

    @Override
    public int getTotalNumOfError() {
        return result.getTotal().getNumOfError();
    }

    @Override
    public void clear() {

        result.clear();
    }

    private class ProcessResult {

        private AtomicInteger numOfError = new AtomicInteger(0);
        private AtomicInteger numOfSuccess = new AtomicInteger(0);
        private AtomicInteger numOfFailed = new AtomicInteger(0);

        private ProcessResult total;

        private void updateTotal() {
            if (total == null) {
                total = new ProcessResult();
            }

            total.numOfError.addAndGet(numOfError.get());
            total.numOfSuccess.addAndGet(numOfSuccess.get());
            total.numOfFailed.addAndGet(numOfFailed.get());
            //System.out.println(this + " total " + total);
        }

        public void clear() {
            updateTotal();
            numOfFailed.set(0);
            numOfSuccess.set(0);
            numOfError.set(0);
        }

        public int getNumOfSuccess() {
            return numOfSuccess.get();
        }

        public int getNumOfFailed() {
            return numOfFailed.get();
        }

        public int getNumOfError() {
            return numOfError.get();
        }

        public void incrementFailed() {
            numOfFailed.incrementAndGet();
        }

        public void incrementSuccess() {
            numOfSuccess.incrementAndGet();
        }

        public void incrementError() {
            numOfError.incrementAndGet();
        }

        public ProcessResult getTotal() {
            return total;
        }

        @Override
        public String toString() {
            return "ProcessResult{" +
                    "numOfError=" + numOfError +
                    ", numOfSuccess=" + numOfSuccess +
                    ", numOfFailed=" + numOfFailed +
                    '}';
        }
    }
}
