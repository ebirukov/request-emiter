package com.drimmi.rtb.reader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.openrtb.json.OpenRtbJsonUtils.endArray;
import static com.google.openrtb.json.OpenRtbJsonUtils.startArray;

public class OpenRtbExtSetter {

    public List<String> parseArray(JsonParser parser) throws IOException {
        List<String> values = new ArrayList<>();

        for (startArray(parser); endArray(parser); parser.nextToken()) {
            values.add(parser.getText());
        }
        return values;
    }

    public OpenRtbExtSetter buildSetter(JsonParser parser) throws IOException {
        String name = parser.getCurrentName();
        JsonToken token = parser.getCurrentToken();
        switch (token) {
            case START_ARRAY:
        }
        return null;
    }
}
