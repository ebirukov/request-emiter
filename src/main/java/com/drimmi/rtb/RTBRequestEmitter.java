package com.drimmi.rtb;

public class RTBRequestEmitter {

    Processor processor;


    public RTBRequestEmitter(Processor processor) {
        this.processor = processor;
    }


    public ProcessResult processRequests(int numOfRequests) {
        ProcessResult processResult = new ProcessResult();
        for (int i = 0; i < numOfRequests; i++) {

            boolean result = processor.sendRequest();
            if (result) {
                processResult.incrementSuccess();
            } else {
                processResult.incrementFailed();
            }

        }
        return processResult;

    }
}
