package com.drimmi.rtb;

import com.google.openrtb.OpenRtb;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.UUID;

import static com.google.protobuf.util.JsonFormat.parser;
import static java.lang.Thread.currentThread;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.Objects.requireNonNull;

public class RTBRequestPrototype implements DataPrototypeAdapter<OpenRtb.BidRequest> {

    private final OpenRtb.BidRequest.Builder builder;

    public RTBRequestPrototype() {
        this.builder = OpenRtb.BidRequest.newBuilder();
        buildProto();
    }

    private RTBRequestPrototype(RTBRequestPrototype prototype) {
        this.builder = prototype.builder;
    }

    @Override
    public RTBRequestPrototype clone() {
        var id = UUID.randomUUID().toString();
        builder.setId(id);
        builder.setImp(0, builder.getImp(0).toBuilder().setId(id + "-1"));
        builder.setUser(builder.getUser().toBuilder().setId("U" + id));
        return new RTBRequestPrototype(this);
    }

    private void buildProto() {
        try (InputStreamReader reader = new InputStreamReader(requireNonNull(currentThread()
                .getContextClassLoader()
                .getResourceAsStream("rtbrequest.json")), defaultCharset())
        ) {

            parser().ignoringUnknownFields().merge(reader, builder);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String asString() {
        try {
            return JsonFormat.printer().print(builder.build());
        } catch (InvalidProtocolBufferException e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ByteBuffer toBinary() {
        return null;
    }
}
