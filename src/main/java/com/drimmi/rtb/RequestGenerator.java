package com.drimmi.rtb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestGenerator {

    private String json;

    public RequestGenerator() {
        try {
            this.json = Files.newBufferedReader(Paths.get("src/test/resources/rtbrequest.json")).lines().collect(Collectors.joining("")).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RTBRequest generate(EmitterConfiguration configuration) {
        var request = new RTBRequest();

        IntStream.range(0, configuration.getNumOfRequests()).mapToObj(i -> json).forEach(request::addContent);
        return request;
    }
}
