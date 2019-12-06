package com.drimmi.rtb;

import java.util.concurrent.atomic.AtomicInteger;

class ProcessResult {

    private AtomicInteger numOfError = new AtomicInteger(0);
    private AtomicInteger numOfSuccess = new AtomicInteger(0);
    private AtomicInteger numOfFailed = new AtomicInteger(0);

    private ProcessResult total;

    private void updateTotal() {
        if (total == null) {
            total = new ProcessResult();
        }

        total.numOfError.addAndGet(numOfError.get());
        total.numOfSuccess.addAndGet(numOfSuccess.get());
        total.numOfFailed.addAndGet(numOfFailed.get());
        //System.out.println(this + " total " + total);
    }

    public void clear() {
        updateTotal();
        numOfFailed.set(0);
        numOfSuccess.set(0);
        numOfError.set(0);
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

    public ProcessResult getTotal() {
        return total;
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
