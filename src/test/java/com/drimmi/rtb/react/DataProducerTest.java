package com.drimmi.rtb.react;

import com.drimmi.rtb.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.concurrent.Flow;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DataProducerTest {

    @Mock
    EmitterConfiguration config;

    @Mock
    RTBRequestGenerator generator;

    @Test
    public void startPublish() {

        Mockito.when(config.getUrl()).thenReturn("http://test.ru");
        Mockito.when(config.getRequestTimeout()).thenReturn(EmitterConfiguration.DEFAULT_TIMEOUT);

        var data = new String[]{"a", "b", "c", "d", "e", "f"};
        var numOfData = data.length;
        var numOfBatch = 2;

        var builder = Stream.<String>builder();
        Arrays.asList(data).forEach(builder::add);
        when(generator.constructBuilder()).thenReturn(builder);
        doCallRealMethod().when(generator).start(any(Consumer.class));

        HTTPRequestExecutor executor = spy(new HTTPRequestExecutor(config));
        HTTPWorker worker = spy(new HTTPWorker(executor, numOfData, numOfBatch));

        DataProducer dataProducer = new DataProducer(generator);
        dataProducer.subscribe(worker);
        dataProducer.startPublish();

        verify(worker, timeout(1000)).onSubscribe(any(Flow.Subscription.class));
        var captor = ArgumentCaptor.forClass(String.class);
        verify(worker, timeout(1000).times(numOfData)).onNext(captor.capture());
        assertArrayEquals(data, captor.getAllValues().toArray());
    }
}