package com.ondo.pos.model;

public class RefundRequest {
    private String traceno;
    private boolean skipPrint;

    public boolean isSkipPrint() {
        return skipPrint;
    }

    public void setSkipPrint(boolean skipPrint) {
        this.skipPrint = skipPrint;
    }

    public String getTraceno(){
        return traceno;
    }

    public void setTraceno(String traceno) {
        this.traceno = traceno;
    }
}
