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
import java.util.stream.Collectors;

public class RequestExecutor {

    HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
            .uri(URI.create("http://rtb.dmdata.org:8888/rtb/bids/nexage"));
            

    ProcessResult result = new ProcessResult();

    public int execute(RTBRequest rtbRequest) throws IOException {
        var status = 200;
        var json = Files.newBufferedReader(Paths.get("src/test/resources/rtbrequest.json")).lines().collect(Collectors.joining("")).trim();
        HttpRequest httpRequest = httpRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(json)).build();
        client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .thenAccept(this::accumulateResult)
                .join();
        return status;
    }

    private void accumulateResult(int s) {
        System.out.println(s);
    }
}
