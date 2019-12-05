package com.drimmi.rtb;

import java.util.concurrent.atomic.AtomicInteger;

class ProcessResult {

    private AtomicInteger numOfError;
    private AtomicInteger numOfSuccess;
    private AtomicInteger numOfFailed;

    public ProcessResult() {
        numOfFailed = new AtomicInteger(0);
        numOfSuccess = new AtomicInteger(0);
        numOfError = new AtomicInteger(0);
    }

    public int getNumOfSuccess() {
        return numOfSuccess.get();
    }

    public int getNumOfFailed() {
        return numOfFailed.get();
    }

    public int getNumOfError() {
        return numOfError.get();
    }

    public void incrementFailed() {
        numOfFailed.incrementAndGet();
    }

    public void incrementSuccess() {
        numOfSuccess.incrementAndGet();
    }

    public void incrementError() {
        numOfError.incrementAndGet();
    }

    @Override
    public String toString() {
        return "ProcessResult{" +
                "numOfError=" + numOfError +
                ", numOfSuccess=" + numOfSuccess +
                ", numOfFailed=" + numOfFailed +
                '}';
    }
}
