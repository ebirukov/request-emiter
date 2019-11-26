package com.drimmi.rtb;

import java.util.ArrayList;
import java.util.Collection;

public class RTBRequest {

    private Collection<String> content = new ArrayList<>();

    public Collection<String> getContent() {
        return content;
    }

    public void addContent(String content) {
        this.content.add(content);
    }
}
