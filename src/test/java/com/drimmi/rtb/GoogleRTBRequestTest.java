package com.drimmi.rtb;

import com.google.doubleclick.AdxExt;
import com.google.openrtb.OpenRtb;
import com.google.protobuf.util.JsonFormat;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public void parseRequest() throws IOException {
        OpenRtb.BidRequest.Builder requestBuilder = OpenRtb.BidRequest.newBuilder();

        //Files.lines(rtbrequestPath).forEach(System.out::println);
        JsonFormat.parser().ignoringUnknownFields().merge(rtbrequest, requestBuilder);
        OpenRtb.BidRequest request = requestBuilder.build();
        assertNotNull(request);
        assertNotNull(request.getId());

        //TODO:
    }


}
