package com.drimmi.rtb;

public class ProcessResult {

    private final int numOfSuccess;
    private final int numOfFailed;

    public ProcessResult(int numOfSuccess, int numOfFailed) {
        this.numOfSuccess = numOfSuccess;
        this.numOfFailed = numOfFailed;
    }

    public int getNumOfSuccess() {
        return numOfSuccess;
    }

    public int getNumOfFailed() {
        return numOfFailed;
    }
}
