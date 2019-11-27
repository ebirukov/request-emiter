package com.drimmi.rtb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class RTBRequest {

    private Stream.Builder<String> contentBuilder = Stream.builder();

    public Stream<String> buildContentStream() {
        return contentBuilder.build();
    }

    public void addContent(String content) {
        contentBuilder.add(content);
    }
}
