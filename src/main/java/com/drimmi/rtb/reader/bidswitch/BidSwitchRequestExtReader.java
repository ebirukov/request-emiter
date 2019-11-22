package com.drimmi.rtb.reader.bidswitch;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.openrtb.OpenRtb;
import com.google.openrtb.json.OpenRtbJsonExtComplexReader;
import com.google.protobuf.GeneratedMessage;
import com.powerspace.bidswitch.BidSwitchExt;

import java.io.IOException;

import static com.google.openrtb.json.OpenRtbJsonUtils.*;

class BidSwitchRequestExtReader extends OpenRtbJsonExtComplexReader<OpenRtb.BidRequest.Builder, BidSwitchExt.BidRequestExt.Builder> {

    /**
     * Use this constructor for readers of message type.
     *
     * @param key             Extension key
     * @param isJsonObject    {@code true} if the extension value is desserialized from a JSON object
     * @param rootNameFilters Filter for the root names (direct fields of "ext").
     */
    protected BidSwitchRequestExtReader() {
        super(BidSwitchExt.bidRequestExt, false, "ads_txt", "ssp", "media_src", "google",
                "gumgum", "rubicon", "adtruth", "tv", "dooh", "clktrkrq", "s2s_nurl", "is_secure", "wt");
    }

    @Override
    protected void read(BidSwitchExt.BidRequestExt.Builder ext, JsonParser par) throws IOException {

        switch (getCurrentName(par)) {
            case "ads_txt":
                BidSwitchExt.BidRequestExt.AdsTxt.Builder adsTxt = BidSwitchExt.BidRequestExt.AdsTxt.newBuilder();
                for (startObject(par); endObject(par); par.nextToken()) {
                    String fieldName = getCurrentName(par);
                    if (par.nextToken() != JsonToken.VALUE_NULL) {
                        readAdsTxtField(par, adsTxt, fieldName);
                    }
                }
                break;
            case "ssp":
                ext.setSsp(par.nextTextValue());
                break;
            case "s2s_nurl":
                ext.setS2SNurl(par.nextIntValue(0));
        }
    }

    private void readAdsTxtField(JsonParser par, BidSwitchExt.BidRequestExt.AdsTxt.Builder adsTxt, String fieldName) {
/*        required int32 status = 1001;
        optional string auth_id = 1002;
        required string pub_id = 1003;*/
    }
}
