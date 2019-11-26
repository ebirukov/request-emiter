package com.drimmi.rtb;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationParserTest {

    @Test
    public void testParse() {
        String[] args = {"-url", "http://a.b.c", "--w", "5"};
        var parser = new ConfigurationParser(args);
        EmitterConfiguration config = parser.getConfiguration();
        assertEquals("http://a.b.c", config.getUrl());
        assertEquals(5, config.getNumOfParallelWorker());
        assertEquals(1, config.getNumOfRequests());
    }
}
