package com.drimmi.rtb;

import com.google.openrtb.OpenRtb;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Thread.*;
import static java.nio.charset.Charset.*;
import static java.util.Objects.*;

public class RequestGenerator {

    private String json;

    OpenRtb.BidRequest.Builder requestBuilder;
    private EmitterConfiguration configuration;

    public RequestGenerator(EmitterConfiguration configuration) {
        this.configuration = configuration;
        requestBuilder = OpenRtb.BidRequest.newBuilder();
        try (InputStreamReader reader = new InputStreamReader(requireNonNull(currentThread()
                .getContextClassLoader()
                .getResourceAsStream("rtbrequest.json")), defaultCharset())
            ) {

            JsonFormat.parser().ignoringUnknownFields().merge(reader, requestBuilder);

        } catch (IOException e) {
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
