package com.drimmi.rtb;

public interface JobResult {

    int getNumOfError();

    int getNumOfSuccess();

    int getTotalNumOfSuccess();

    int getTotalNumOfError();

    void clear();

}
