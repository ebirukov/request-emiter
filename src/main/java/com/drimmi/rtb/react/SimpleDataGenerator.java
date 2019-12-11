package com.drimmi.rtb.react;

import java.util.Arrays;
import java.util.stream.Stream;

public class SimpleDataGenerator extends GenericDataGenerator<String> {

    private Iterable<String> data;

    public SimpleDataGenerator(String... data) {
        this.data = Arrays.asList(data);
    }

    @Override
    protected Stream.Builder<String> constructBuilder() {
        Stream.Builder builder = Stream.builder();
        data.forEach(builder::add);
        return builder;
    }
}
