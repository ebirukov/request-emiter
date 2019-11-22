package com.drimmi.rtb;

import com.drimmi.rtb.reader.bidswitch.BidSwitchExtUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.google.doubleclick.*;

import static com.google.openrtb.OpenRtb.*;

import com.google.doubleclick.openrtb.json.AdxExtUtils;
import com.google.openrtb.OpenRtb;
import com.google.openrtb.json.OpenRtbJsonExtReader;
import com.google.openrtb.json.OpenRtbJsonFactory;
import com.google.protobuf.Any;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.TextFormat;
import com.google.protobuf.util.JsonFormat;
import org.junit.Before;
import org.junit.Ignore;
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

import static com.google.openrtb.json.OpenRtbJsonUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GoogleRTBRequestTest {

    BufferedReader rtbrequest;
    Path rtbrequestPath;

    @Before
    public void init() throws IOException {
        rtbrequestPath = Paths.get("src/test/resources/google_rtbrequest.json");
        rtbrequest = Files.newBufferedReader(rtbrequestPath);

    }

    @Test
    public  void testJsonFactory() throws IOException {
        OpenRtbJsonFactory openRTBJson = OpenRtbJsonFactory.create()
                /*.register(reader, BidRequest.Imp.Builder.class )*/
                ;
        AdxExtUtils.registerAdxExt(openRTBJson);

        BidRequest request = openRTBJson.newReader().readBidRequest(rtbrequest);

        System.out.println(JsonFormat.printer().print(request));
        assertEquals(request.getImp(0).getInstl(), false);
    }

    @Test
    @Ignore
    public void parseRequest() throws IOException {

        JsonFormat.TypeRegistry typeRegistry = JsonFormat.TypeRegistry.newBuilder()
                .add(Any.getDescriptor())
                .build();

        ExtensionRegistry registry = ExtensionRegistry.newInstance();

        BidRequest.Builder requestBuilder = BidRequest.newBuilder();
        JsonFormat.Parser parser = JsonFormat.parser().usingTypeRegistry(typeRegistry);

        parser.ignoringUnknownFields().merge(rtbrequest, requestBuilder);
        BidRequest request = requestBuilder.build();


        assertNotNull(request);
        assertNotNull(request.getId());
        //assertEquals("1", request.getExtension(AdxExt.bidRequest).getGoogleQueryId());
        //TODO:
    }


}
