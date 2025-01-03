package com.example.transaction_recorder.controller;

import com.example.transaction_recorder.model.TransactionRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@RestController
@RequestMapping("/api/v1")
public class RegisterController {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.queueUrl}")
    private String queueUrl;

    public RegisterController(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @PostMapping("/register")
    public String registerTransaction(@RequestBody TransactionRequest request) {
        try {
            // SQSに送信するJSONメッセージを作成
            String messageBody = String.format(
                    "{\"user_id\":\"%s\", \"amount\":\"%s\", \"transaction_type\":\"%s\", \"memo\":\"%s\"}",
                    request.getUserId(), request.getAmount(), request.getTransactionType(), request.getMemo()
            );

            // SQSにメッセージを送信
            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build());

            return "購入履歴が送信されました！";
        } catch (Exception e) {
            e.printStackTrace();
            return "購入履歴の送信に失敗しました。";
        }
    }
}

