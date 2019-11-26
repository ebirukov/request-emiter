package com.drimmi.rtb;

import com.beust.jcommander.JCommander;

public class ConfigurationParser {

    private EmitterConfiguration configuration;

    public ConfigurationParser(String[] args) {
        this.configuration = new EmitterConfiguration();
        JCommander.newBuilder()
                .addObject(configuration)
                .build()
                .parse(args);
    }

    public EmitterConfiguration getConfiguration() {
        return configuration;
    }
}
