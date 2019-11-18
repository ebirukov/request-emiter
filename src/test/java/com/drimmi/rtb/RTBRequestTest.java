package com.drimmi.rtb;

import com.google.doubleclick.AdxExt;
import com.google.openrtb.OpenRtb;
import com.google.protobuf.Extension;
import com.google.protobuf.ExtensionRegistry;
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
import java.util.Set;
import java.util.UUID;

import static com.google.openrtb.OpenRtb.*;
import static org.junit.Assert.*;
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
        assertSite(request.getSite());
        assertDevice(request.getDevice());
        assertUser(request.getUser());

        assertEquals(AuctionType.SECOND_PRICE, request.getAt());

    }

    private void assertUser(BidRequest.User user) {
        assertNotNull(user);
        assertEquals("ASDFJKL", user.getId());
        assertEquals(1961, user.getYob());
        assertEquals("F", user.getGender());
        assertEquals("sports", user.getKeywords());
        assertNotNull(user.getGeo());
        assertEquals("USA", user.getGeo().getCountry());
        assertEquals("Waltham", user.getGeo().getCity());
        assertEquals("02451", user.getGeo().getZip());
        assertEquals("MA", user.getGeo().getRegion());
        assertEquals(LocationType.USER_PROVIDED, user.getGeo().getType());

        //System.out.println(infos);
    }

    private void assertDevice(BidRequest.Device device) {
        assertNotNull(device);
        assertEquals("f22711a823044bb9ce7ace097955de0286eb0182", device.getDpidsha1());
        assertEquals("132079238ec783b0b89dff308e1f9bdd08576273", device.getDidsha1());
        assertEquals("ATT", device.getCarrier());
        assertEquals("166.137.138.18", device.getIp());
        assertEquals("Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_2_1 like Mac OS X; el-gr) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5", device.getUa());
        assertEquals("Apple", device.getMake());
        assertEquals("iPhone", device.getModel());
        assertEquals("3.1.2", device.getOsv());
        assertEquals(ConnectionType.CELL_UNKNOWN, device.getConnectiontype());
        assertEquals(DeviceType.MOBILE, device.getDevicetype());
        assertNotNull(device.getGeo());
        assertEquals(42.378d, device.getGeo().getLat(), 0.1);
        assertEquals(-71.227d, device.getGeo().getLon(), 0.1);
        assertEquals("USA", device.getGeo().getCountry());
    }

    private void assertSite(BidRequest.Site site) {
        assertNotNull(site);
        assertEquals("99201", site.getId());
        assertEquals("BidderTestMobileWEB", site.getName());
        assertEquals("junk1.com", site.getDomain());
        assertEquals(2, site.getCatList().size());
        assertEquals("IAB1", site.getCat(0));
        assertEquals("IAB2", site.getCat(1));
        assertEquals("radiation", site.getKeywords());
        assertEquals("http://www.nexage.com", site.getPage());
        assertEquals("http://www.iab.net", site.getRef());
        assertEquals("radiation", site.getSearch());
        assertNotNull(site.getPublisher());
        assertEquals("98401", site.getPublisher().getId());
        assertEquals("testme", site.getPublisher().getName());

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
