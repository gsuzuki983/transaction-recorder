package com.example.transaction_recorder.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionRequest {
    @JsonProperty("user_id")
    private String userId;
    private String amount;
    @JsonProperty("transaction_type")
    private String transactionType;
    private String memo;

    // ゲッターとセッターを追加
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}