package com.drimmi.rtb;

public class ProcessResult {

    private int numOfSuccess;
    private int numOfFailed;

    public ProcessResult() {
    }

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

    public void incrementFailed() {
        numOfFailed += 1;
    }

    public void incrementSuccess() {
        numOfSuccess += 1;
    }
}
