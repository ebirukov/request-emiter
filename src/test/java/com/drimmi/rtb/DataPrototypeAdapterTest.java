package com.drimmi.rtb;

import com.google.openrtb.OpenRtb.BidRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DataPrototypeAdapterTest {

    @Spy BidRequest.Builder builder;

    @Test
    public void cloneTest() {
        DataPrototypeAdapter<BidRequest> dataPrototypeAdapter = new RTBRequestPrototype();
        var dataPrototype = dataPrototypeAdapter.clone();
        assertNotNull(dataPrototype);
        assertTrue(dataPrototype.getClass().equals(dataPrototypeAdapter.getClass()));

    }

    @Test
    public void asStringTest() {
        DataPrototypeAdapter<BidRequest> dataPrototypeAdapter = new RTBRequestPrototype();
        assertNotNull(dataPrototypeAdapter.asString());
    }
}
