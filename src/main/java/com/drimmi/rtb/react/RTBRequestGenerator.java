package com.drimmi.rtb.react;

import com.drimmi.rtb.DataPrototypeAdapter;

import java.util.stream.Stream;

public class RTBRequestGenerator extends GenericDataGenerator<String> {

    private final int numOfData;
    private final DataPrototypeAdapter prototype;

    public RTBRequestGenerator(int numOfData, DataPrototypeAdapter prototypeAdapter) {
        this.numOfData = numOfData;
        this.prototype = prototypeAdapter;
    }

    @Override
    protected Stream.Builder<String> constructBuilder() {
        var contentBuilder = Stream.<String>builder();
        for (int i = 0; i < numOfData; i++) {
            contentBuilder.add(prototype.clone().asString());
        }
        return contentBuilder;
    }
}
