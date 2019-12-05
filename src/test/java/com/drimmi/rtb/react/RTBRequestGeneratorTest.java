package com.drimmi.rtb.react;

import com.drimmi.rtb.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.Flow;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RTBRequestGeneratorTest {

    @Mock
    EmitterConfiguration config;

    @Mock
    RequestGenerator generator;

    @Mock
    RTBRequest request;

    @Test
    public void startPublish() {

        Mockito.when(config.getUrl()).thenReturn("http://test.ru");
        Mockito.when(config.getRequestTimeout()).thenReturn(EmitterConfiguration.DEFAULT_TIMEOUT);

        var data = new String[]{"a", "b", "c", "d", "e", "f"};
        var numOfData = data.length;
        var numOfBatch = 2;

        when(request.buildContentStream()).thenReturn(Stream.of(data));
        when(generator.generate()).thenReturn(request);

        HTTPRequestExecutor executor = spy(new HTTPRequestExecutor(config));
        HTTPWorker worker = spy(new HTTPWorker(executor, numOfData, numOfBatch));

        RTBRequestGenerator requestGenerator = new RTBRequestGenerator(generator);
        requestGenerator.subscribe(worker);
        requestGenerator.startPublish();

        verify(worker, timeout(1000)).onSubscribe(any(Flow.Subscription.class));
        var captor = ArgumentCaptor.forClass(String.class);
        verify(worker, timeout(1000).times(numOfData)).onNext(captor.capture());
        assertArrayEquals(data, captor.getAllValues().toArray());
    }
}