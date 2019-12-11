package com.drimmi.rtb.react;

import java.nio.ByteBuffer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class GenericDataGenerator<T> {

    private Consumer<T> consumer;

    public void start(Consumer<T> consumer) {
        this.consumer = consumer;
        constructBuilder().build().forEach(consumer);
    }

    protected abstract Stream.Builder<T> constructBuilder();

}
