package com.drimmi.rtb;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class RequestExecutorTest {

    public static final int REQUEST_COUNT = 1;
    private final ConfigurationSupport configurationSupport = new ConfigurationSupport();

    @Test
    public void executeTest() throws IOException {
        EmitterConfiguration config = configurationSupport.getConfiguration();

        RequestExecutor requestExecutor = new RequestExecutor(config);

        var generator = new RequestGenerator(config);
        RTBRequest request = generator.generate();

//        var json = Files.newBufferedReader(Paths.get("src/test/resources/rtbrequest.json")).lines().collect(Collectors.joining("")).trim();
//
//        IntStream.range(0, config.getNumOfRequests()).mapToObj(i -> json).forEach(request::addContent);



        ProcessResult processResult = requestExecutor.execute(request);

        assertNotNull(processResult);
        assertEquals(REQUEST_COUNT, processResult.getNumOfSuccess() + processResult.getNumOfFailed());
        System.err.println(processResult.getNumOfFailed());
        System.out.println(processResult.getNumOfSuccess());
        System.err.println("error status: " + processResult.getNumOfError());
    }
}
