package com.drimmi.rtb.react;

import com.drimmi.rtb.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.Spy;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.mockito.verification.After;
import org.mockito.verification.VerificationAfterDelay;
import org.mockito.verification.VerificationMode;

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

        var data = new String[]{"a", "b", "c", "d"};
        var numOfData = data.length;

        when(request.buildContentStream()).thenReturn(Stream.of(data));

        RequestExecutor executor = spy(new RequestExecutor(config));

        HTTPWorker worker = spy(new HTTPWorker(executor, 2));

        RTBRequestGenerator requestGenerator = new RTBRequestGenerator(generator);
        when(generator.generate()).thenReturn(request);

        requestGenerator.subscribe(worker);
        requestGenerator.startPublish();
        verify(worker, timeout(100)).onSubscribe(any(Flow.Subscription.class));
        var captor = ArgumentCaptor.forClass(String.class);
        verify(worker, timeout(100).times(numOfData)).onNext(captor.capture());
        assertArrayEquals(data, captor.getAllValues().toArray());
    }
}