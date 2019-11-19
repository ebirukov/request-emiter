package com.drimmi.rtb;

import com.fasterxml.jackson.core.JsonParser;
import com.google.doubleclick.*;

import static com.google.openrtb.OpenRtb.*;

import com.google.openrtb.OpenRtb;
import com.google.openrtb.json.OpenRtbJsonExtReader;
import com.google.openrtb.json.OpenRtbJsonFactory;
import com.google.protobuf.Any;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.TextFormat;
import com.google.protobuf.util.JsonFormat;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GoogleRTBRequestTest {

    BufferedReader rtbrequest;
    Path rtbrequestPath;

    @Before
    public void init() throws IOException {
        rtbrequestPath = Paths.get("src/test/resources/google_rtbrequest.json");
        rtbrequest = Files.newBufferedReader(rtbrequestPath);
        AdxExt.registerAllExtensions(ExtensionRegistry.newInstance());

    }

    @Test
    public  void testJsonFactory() throws IOException {
        OpenRtbJsonExtReader<BidRequest.Imp.Builder> reader = new OpenRtbJsonExtReader<BidRequest.Imp.Builder>() {
            @Override
            protected void read(BidRequest.Imp.Builder msg, JsonParser par) throws IOException {
                System.err.println(par.getText());
                msg.setExtension(AdxExt.imp, ImpExt.newBuilder().addAllBillingId(Arrays.asList(1L)).build());

            }
        };
        OpenRtbJsonFactory openRTBJson = OpenRtbJsonFactory.create()
                .register(reader, BidRequest.Imp.Builder.class )
                ;

        BidRequest request = openRTBJson.newReader().readBidRequest(rtbrequest);

        System.out.println(JsonFormat.printer().print(request));
        assertEquals(request.getImp(0).getInstl(), false);
    }

    @Test
    public void parseRequest() throws IOException {
        //ExtensionRegistry reg = ExtensionRegistry.newInstance();
        //AdxExt.registerAllExtensions(reg);
        //OpenRtb.BidRequest.Imp.newBuilder().set
/*        AdxExt.ImpExt.newBuilder()
                .setField()
                .build()
        Any.pack()*/


        JsonFormat.TypeRegistry typeRegistry = JsonFormat.TypeRegistry.newBuilder()
                .add(Any.getDescriptor())
                .add(BidResponseExt.getDescriptor())
                .build();

        ExtensionRegistry registry = ExtensionRegistry.newInstance();

        BidRequest.Builder requestBuilder = BidRequest.newBuilder()
/*            .setId("1")
            .addImp(BidRequest.Imp.newBuilder()
                    //.setExtension("ext", ImpExt.newBuilder().addAllBillingId(Arrays.asList(1L)).build())
                    .setInstl(true)
                    .setId("123")
                    .build())*/

            ;
        //System.out.println(JsonFormat.printer().print(requestBuilder.build()));
        //Files.lines(rtbrequestPath).forEach(System.out::println);
        JsonFormat.Parser parser = JsonFormat.parser().usingTypeRegistry(typeRegistry);




        parser.ignoringUnknownFields().merge(rtbrequest, requestBuilder);
        //TextFormat.getParser().merge(rtbrequest, reg, requestBuilder);
        BidRequest request = requestBuilder.build();

/*        AdxExt.ImpExt.parseFrom(
                imp.getUnknownFields().getField(AdxExt.imp.getNumber())
                        .getLengthDelimitedList().get(0));*/

        assertNotNull(request);
        assertNotNull(request.getId());
        //assertEquals("1", request.getExtension(AdxExt.bidRequest).getGoogleQueryId());
        //TODO:
    }


}
