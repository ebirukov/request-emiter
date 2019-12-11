package com.drimmi.rtb.react;

import com.drimmi.rtb.EmitterConfiguration;
import com.google.openrtb.OpenRtb;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.stream.Stream;

import static java.lang.Thread.*;
import static java.nio.charset.Charset.*;
import static java.util.Objects.*;

public class RTBRequestGenerator extends GenericDataGenerator<String> {

    private OpenRtb.BidRequest.Builder requestBuilder;
    private final int numOfData;

    public RTBRequestGenerator(int numOfData) {
        this.numOfData = numOfData;
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

    private String ToJson(JsonFormat.Printer jsonPrinter) throws InvalidProtocolBufferException {
        var json = jsonPrinter.print(requestBuilder.build());
        return json;
    }

    @Override
    protected Stream.Builder<String> constructBuilder() {
        var contentBuilder = Stream.<String>builder();
        var jsonPrinter = JsonFormat.printer();
        for (int i = 0; i < numOfData; i++) {
            try {
                var id = UUID.randomUUID().toString();
                requestBuilder.setId(id);
                requestBuilder.setImp(0, requestBuilder.getImp(0).toBuilder().setId(id + "-1"));
                requestBuilder.setUser(requestBuilder.getUser().toBuilder().setId("U" + id));
                contentBuilder.add(ToJson(jsonPrinter));
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
        return contentBuilder;
    }
}
