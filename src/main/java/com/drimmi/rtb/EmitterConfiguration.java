package com.drimmi.rtb;

import com.beust.jcommander.Parameter;

public class EmitterConfiguration {

    public static final String DEFAULT_URL = "http://ec2-54-242-11-157.compute-1.amazonaws.com:8080/rtb/bids/nexage";
    public static final int DEFAULT_WORKERS = 8;
    public static final int DEFAULT_TIMEOUT = 4000;

    @Parameter(names = {"-url", "--u"}, description = "URL to send requests, default rtb.dmdata.org")
    private String url = DEFAULT_URL;

    @Parameter(names = {"-workers", "--w"}, description = "number of parallel worker sending requests")
    private int numOfParallelWorker = DEFAULT_WORKERS;

    @Parameter(names = {"-requests", "--nr"}, description = "total number of requests")
    private int numOfRequests = 1;

    @Parameter(names = {"-requestTimeout", "--rt"}, description = "request timeout in millisecond")
    private int requestTimeout = DEFAULT_TIMEOUT;

    @Parameter(names = {"-numOfBatch", "--nb"}, description = "number of batch request packet")
    private int batchSize = 1;

    public String getUrl() {
        return url;
    }

    public int getNumOfParallelWorker() {
        return numOfParallelWorker;
    }

    public int getNumOfRequests() {
        return numOfRequests;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public int getBatchSize() {
        return batchSize;
    }
}
