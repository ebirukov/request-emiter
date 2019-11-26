package com.drimmi.rtb;

import java.util.concurrent.atomic.AtomicInteger;

public class ProcessResult {

    private AtomicInteger numOfSuccess;
    private AtomicInteger numOfFailed;

    public ProcessResult() {
        numOfFailed = new AtomicInteger(0);
        numOfSuccess = new AtomicInteger(0);
    }

    public ProcessResult(int numOfSuccess, int numOfFailed) {
        this.numOfSuccess = new AtomicInteger(numOfSuccess);
        this.numOfFailed = new AtomicInteger(numOfFailed);
    }

    public int getNumOfSuccess() {
        return numOfSuccess.get();
    }

    public int getNumOfFailed() {
        return numOfFailed.get();
    }

    public void incrementFailed() {
        numOfFailed.incrementAndGet();
    }

    public void incrementSuccess() {
        numOfSuccess.incrementAndGet();
    }
}
