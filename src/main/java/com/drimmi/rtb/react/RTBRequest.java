package com.drimmi.rtb.react;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

class RTBRequest {

    private Stream.Builder<String> contentBuilder = Stream.builder();

    public Stream<String> buildContentStream() {
        return contentBuilder.build();
    }

    public void addContent(String content) {
        contentBuilder.add(content);
    }
}
