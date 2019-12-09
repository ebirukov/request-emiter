package com.drimmi.rtb.react;

import com.drimmi.rtb.JobResult;

import java.util.Objects;

public class BatchOptimizer {

    private final JobResult jobResult;

    public BatchOptimizer(JobResult jobResult) {
        Objects.requireNonNull(jobResult);
        this.jobResult = jobResult;
    }


    public int nextBatch(int numOfBatch) {
        var numOfNextBatch = calcNextBatch(numOfBatch);
        if (jobResult.getNumOfError() > 0) System.out.println(" numOfError " + jobResult.getNumOfError() + ": next numOfBatch " + numOfBatch);
        jobResult.clear();
        return numOfNextBatch;
    }

    private int calcNextBatch(int numOfBatch) {
        if (jobResult.getNumOfError() > 0) {
            numOfBatch -= (numOfBatch / 2);
        } else {
            numOfBatch += (numOfBatch / 2);
        }
        return numOfBatch < 1 ? 1 : numOfBatch;
    }
}
