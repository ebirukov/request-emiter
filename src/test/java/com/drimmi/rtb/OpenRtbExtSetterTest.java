package com.drimmi.rtb;

import com.drimmi.rtb.reader.OpenRtbExtSetter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.openrtb.OpenRtb;
import com.google.protobuf.MessageOrBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.internal.stubbing.defaultanswers.ReturnsSmartNulls;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class OpenRtbExtSetterTest {

    OpenRtbExtSetter extSetter = new OpenRtbExtSetter();



    @Test
    @Ignore
    public void testBuildSetter() throws IOException {
        JsonParser parser =  mock(JsonParser.class);
        //MessageOrBuilder message = mock(MessageOrBuilder.class);
        OpenRtbExtSetter setter = extSetter.buildSetter(parser);
        assertNotNull(setter);
        assertTrue(setter instanceof OpenRtbExtSetter);
    }


    @Test
    public void testSetExt() throws IOException {
        JsonParser parser =  mock(JsonParser.class);

        when(parser.nextToken())
                .thenReturn(JsonToken.START_ARRAY)
                .thenReturn(JsonToken.VALUE_NUMBER_INT)
                .thenReturn(JsonToken.VALUE_NUMBER_INT)
                .thenReturn(JsonToken.END_ARRAY);

        when(parser.getText())
                .thenReturn("456")
                .thenReturn("123");

        when(parser.getCurrentToken())
                //.thenReturn(JsonToken.FIELD_NAME)
                .thenReturn(JsonToken.START_ARRAY)
                .thenReturn(JsonToken.VALUE_NUMBER_INT)
                .thenReturn(JsonToken.VALUE_NUMBER_INT)
                .thenReturn(JsonToken.END_ARRAY);
/*
        when(parser.getCurrentName())
                .thenReturn("billingId")
                .thenReturn("[")
                .thenReturn("123")
                .thenReturn("]");*/

        List<String> res = extSetter.parseArray(parser);
        assertArrayEquals(new String[]{"456", "123"}, res.toArray());
        verify(parser,  times(3)).nextToken();
        verify(parser,  times(2)).getText();
        verify(parser,  times(4)).getCurrentToken();
    }
}
