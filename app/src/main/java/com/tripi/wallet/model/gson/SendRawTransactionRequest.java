package com.tripi.wallet.model.gson;

public class SendRawTransactionRequest {
    private String data;
    private Integer allowHighFee;

    public SendRawTransactionRequest(String data, Integer allowHighFee) {
        this.data = data;
        this.allowHighFee = allowHighFee;
    }
}
