package com.drimmi.rtb;

public class RTBRequestProcessor {

    ISenderService senderService;


    public RTBRequestProcessor(ISenderService senderService) {
        this.senderService = senderService;
    }


    public ProcessResult processRequests(int numOfRequests) {
        ProcessResult processResult = new ProcessResult();
        for (int i = 0; i < numOfRequests; i++) {

            boolean result = senderService.sendRequest();
            if (result) {
                processResult.incrementSuccess();
            } else {
                processResult.incrementFailed();
            }

        }
        return processResult;

    }
}
