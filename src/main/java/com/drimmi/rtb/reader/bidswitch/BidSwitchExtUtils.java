package com.drimmi.rtb.reader.bidswitch;

import com.google.openrtb.OpenRtb;
import com.google.openrtb.json.OpenRtbJsonFactory;

public class BidSwitchExtUtils {

    public static OpenRtbJsonFactory registerBidSwitchExt(OpenRtbJsonFactory factory) {
        return factory.register(new BidSwitchRequestExtReader(), OpenRtb.BidRequest.Builder.class);
    }

}
