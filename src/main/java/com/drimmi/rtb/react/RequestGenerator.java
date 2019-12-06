package com.drimmi.rtb.react;

import com.drimmi.rtb.EmitterConfiguration;
import com.drimmi.rtb.react.RTBRequest;
import com.google.openrtb.OpenRtb;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import static java.lang.Thread.*;
import static java.nio.charset.Charset.*;
import static java.util.Objects.*;

public class RequestGenerator {

    private String json;

    private OpenRtb.BidRequest.Builder requestBuilder;
    private EmitterConfiguration configuration;

    public RequestGenerator(EmitterConfiguration configuration) {
        this.configuration = configuration;
        buildProto();
    }

    // TODO: Заменить прототипом
    private void buildProto() {
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
