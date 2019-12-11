package com.drimmi.rtb;

import com.drimmi.rtb.react.GenericDataGenerator;
import com.drimmi.rtb.react.SimpleDataGenerator;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class SimpleDataGeneratorTest {

    @Test
    public void startTest() {

        GenericDataGenerator<String> dataGenerator = spy(new SimpleDataGenerator("1", "2", "3"));
/*
        doAnswer(answer -> {
            var consumer = (Consumer<ByteBuffer>) answer.getArgument(0);
            consumer.accept(stringToBuffer("1"));
            consumer.accept(stringToBuffer("2"));
            consumer.accept(stringToBuffer("3"));
            return null;
        })
        .when(dataGenerator).start(any(Consumer.class));*/

        dataGenerator.start(this::processData);

        verify(dataGenerator).start(any(Consumer.class));
    }

    private ByteBuffer stringToBuffer(String s) {
        return ByteBuffer.wrap(s.getBytes());
    }

    private void processData(String item) {
        System.out.println(item);
    }

    private void processData(ByteBuffer byteBuffer) {
        System.out.println(new String(byteBuffer.array()));
    }
}
