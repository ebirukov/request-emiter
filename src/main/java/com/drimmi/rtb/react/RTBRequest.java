package com.drimmi.rtb.react;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

class RTBRequest {

    public Stream.Builder<String> contentBuilder = Stream.builder();

    public Stream<String> buildContentStream() {
        return contentBuilder.build();
    }

    public Stream.Builder<String> getContentBuilder() {
        return contentBuilder;
    }

    public void addContent(String content) {
        contentBuilder.add(content);
    }
}
