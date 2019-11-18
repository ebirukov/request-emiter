package com.drimmi.rtb;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.UUID;

import static com.google.openrtb.OpenRtb.*;

public class RTBRequestTest {

    @Test
    public void test() {
        BidRequest request =
        BidRequest.newBuilder()
                .setId(UUID.randomUUID().toString())
                .addCur("US")
                .build();
        assertEquals("US", request.getCur(0));
        System.out.println(request);
    }
}
