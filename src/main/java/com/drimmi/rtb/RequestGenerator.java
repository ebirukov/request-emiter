package com.drimmi.rtb;

import com.google.openrtb.OpenRtb;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestGenerator {

    private String json;

    OpenRtb.BidRequest.Builder requestBuilder;
    private EmitterConfiguration configuration;

    public RequestGenerator(EmitterConfiguration configuration) {
        this.configuration = configuration;
        try {
            Path path = Paths.get(Thread.currentThread()
                            .getContextClassLoader()
                            .getResource("rtbrequest.json").toURI());
            var rtbRequest = Files.newBufferedReader(path);
            //this.json = Files.newBufferedReader(Paths.get("src/test/resources/rtbrequest.json")).lines().collect(Collectors.joining("")).trim();
            requestBuilder = OpenRtb.BidRequest.newBuilder();
            JsonFormat.parser().ignoringUnknownFields().merge(rtbRequest, requestBuilder);

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public RTBRequest generate() {
        var rtbRequest = new RTBRequest();
        var jsonPrinter = JsonFormat.printer();
        for (int i = 0; i < configuration.getNumOfRequests(); i++) {
            try {
                var id = UUID.randomUUID().toString();
                requestBuilder.setId(id);
                requestBuilder.setImp(0, requestBuilder.getImp(0).toBuilder().setId(id + "-1"));
                requestBuilder.setUser(requestBuilder.getUser().toBuilder().setId("U" + id));
                rtbRequest.addContent(ToJson(jsonPrinter));
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
        //IntStream.range(0, configuration.getNumOfRequests()).mapToObj(i -> json).forEach(rtbRequest::addContent);
        return rtbRequest;
    }

    private String ToJson(JsonFormat.Printer jsonPrinter) throws InvalidProtocolBufferException {
        var json = jsonPrinter.print(requestBuilder.build());
        //System.out.println(json);
        return json;
    }
}
