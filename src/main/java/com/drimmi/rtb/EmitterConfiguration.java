package com.drimmi.rtb;

import com.beust.jcommander.Parameter;

public class EmitterConfiguration {

    public static final String DEFAULT_URL = "http://rtb.dmdata.org:8888/rtb/bids/nexage";
    public static final int DEFAULT_WORKERS = 4;

    @Parameter(names = {"-url", "--u"}, description = "URL to send requests, default rtb.dmdata.org")
    private String url = DEFAULT_URL;

    @Parameter(names = {"-workers", "--w"}, description = "number of parallel worker sending requests")
    private int numOfParallelWorker = DEFAULT_WORKERS;

    @Parameter(names = {"-requests", "--nr"}, description = "total number of requests")
    public int numOfRequests = 1;

    public String getUrl() {
        return url;
    }

    public int getNumOfParallelWorker() {
        return numOfParallelWorker;
    }

    public int getNumOfRequests() {
        return numOfRequests;
    }
}
