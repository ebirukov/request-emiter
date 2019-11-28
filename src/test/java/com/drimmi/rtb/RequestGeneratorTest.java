package com.drimmi.rtb;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestGeneratorTest {

    @Test
    public void generate() {
        var conf = new ConfigurationSupport().buildConfiguration(3);
        var generator = new RequestGenerator(conf);

        var request = generator.generate();
        assertNotNull(request);

        var stream = request.buildContentStream();
        stream.forEach(Assert::assertNotNull);
    }
}