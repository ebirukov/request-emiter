package com.drimmi.rtb.reader.bidswitch;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.openrtb.OpenRtb;
import com.google.openrtb.json.OpenRtbJsonExtComplexReader;
import com.google.protobuf.GeneratedMessage;
import com.powerspace.bidswitch.BidSwitchExt;

import java.io.IOException;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

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

/*    interface Setter { void set (BidSwitchExt.BidRequestExt.Builder ext, JsonParser par); }

    public void operateListWith(Integer integer, Function<Integer, String> method) {
        method.apply(integer);
    }

    public void operateListWith(BidSwitchExt.BidRequestExt.Builder ext, Function<JsonParser, String> method) {
        for (Stuff stuff : listStuff) {
            System.out.println(method.apply(stuff));
        }
    }*/

    void setObjectField(JsonParser par, Consumer<String> acceptor) {
        try {
            for (startObject(par); endObject(par); par.nextToken()) {
                String fieldName = getCurrentName(par);
                if (par.nextToken() != JsonToken.VALUE_NULL) {
                    acceptor.accept(fieldName);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void read(BidSwitchExt.BidRequestExt.Builder ext, JsonParser par) throws IOException {

        switch (getCurrentName(par)) {
            case "ads_txt":
                BidSwitchExt.BidRequestExt.AdsTxt.Builder adsTxt = BidSwitchExt.BidRequestExt.AdsTxt.newBuilder();
                System.out.println(adsTxt.getDescriptorForType().getName());
                setObjectField(par, fieldName -> readAdsTxtField(par, adsTxt, fieldName));
                ext.setAdsTxt(adsTxt);
                break;
            case "ssp":
                ext.setSsp(par.nextTextValue());
                break;
            case "s2s_nurl":
                ext.setS2SNurl(par.nextIntValue(0));
        }
    }

    private void readAdsTxtField(JsonParser par, BidSwitchExt.BidRequestExt.AdsTxt.Builder adsTxt, String fieldName) {
        try {
            switch (getCurrentName(par)) {
                case "status":
                    adsTxt.setStatus(par.nextIntValue(0));
                    break;
                case "auth_id":
                    adsTxt.setAuthId(par.nextTextValue());
                    break;
                case "pub_id":
                    adsTxt.setPubId(par.nextTextValue());
                default:
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


/*        required int32 status = 1001;
        optional string auth_id = 1002;
        required string pub_id = 1003;*/
    }
}
