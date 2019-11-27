package com.drimmi.rtb;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestGeneratorTest {

    @Test
    public void generate() {
        var generator = new RequestGenerator();
        var conf = new ConfigurationSupport().getConfiguration();
        var request = generator.generate(conf);
        assertNotNull(request);

        var stream = request.buildContentStream();
        stream.forEach(Assert::assertNotNull);
    }
}