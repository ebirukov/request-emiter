package com.drimmi.rtb;

import static org.junit.Assert.*;

import com.google.openrtb.OpenRtb;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.google.openrtb.OpenRtb.*;
import static org.junit.Assert.assertEquals;

public class RTBRequestTest {

    BufferedReader rtbrequest;
    Path rtbrequestPath;

    @Before
    public void init() throws IOException {
        rtbrequestPath = Paths.get("src/test/resources/rtbrequest.json");
        rtbrequest = Files.newBufferedReader(rtbrequestPath);
    }

    @Test
    public void test() throws IOException {
        BidRequest.Builder requestBuilder = BidRequest.newBuilder();

        Files.lines(rtbrequestPath).forEach(System.out::println);
        JsonFormat.parser().ignoringUnknownFields().merge(rtbrequest, requestBuilder);
        BidRequest request = requestBuilder.build();
        assertNotNull(request);
        assertNotNull(request.getId());

        assertEquals(1, request.getImpList().size());

        BidRequest.Imp imp = request.getImp(0);
        assertFalse(imp.getInstl());
        assertBanner(imp.getBanner());
        //assertEquals(0, imp.getExtensionCount());

        assertEquals(AuctionType.SECOND_PRICE, request.getAt());


        //System.out.println(JsonFormat.printer().print(request));
    }

    private void assertBanner(BidRequest.Imp.Banner banner) {
        assertNotNull(banner);
        assertEquals(50, banner.getH());
        assertEquals(320, banner.getW());

        List<String> mimeList = banner.getMimesList();
        assertEquals(4, mimeList.size());
        assertEquals("image/gif", mimeList.get(0));
        assertEquals("image/jpg", mimeList.get(1));
        assertEquals("image/png", mimeList.get(2));
        assertEquals("text/javascript", mimeList.get(3));

        assertEquals(AdPosition.UNKNOWN, banner.getPos());
    }
}
