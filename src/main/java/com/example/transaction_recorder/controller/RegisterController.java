package com.example.transaction_recorder.controller;

import com.example.transaction_recorder.model.TransactionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@RestController
@RequestMapping("/api/v1")
public class RegisterController {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.queueUrl}")
    private String queueUrl;

    public RegisterController(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    // リクエストボディを受け取るよう修正
    @PostMapping("/register")
    public String registerTransaction(@RequestBody TransactionRequest request) {
        try {
            // SQSに送信するJSONメッセージを作成
            String messageBody = String.format(
                "{\"user_id\":\"%s\", \"amount\":\"%s\", \"transaction_type\":\"%s\", \"memo\":\"%s\"}",
                request.getUserId(), request.getAmount(), request.getTransactionType(), request.getMemo()
            );

            // ログで確認
            System.out.println("Message Body: " + messageBody);

            // SQSにメッセージを送信
            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();

            // SQSへの送信レスポンスを取得
            SendMessageResponse response = sqsClient.sendMessage(sendMessageRequest);
            System.out.println("Message ID: " + response.messageId());
            System.out.println("HTTP Status: " + response.sdkHttpResponse().statusCode());

            return "購入履歴が送信されました！";
        } catch (Exception e) {
            e.printStackTrace();
            return "購入履歴の送信に失敗しました。";
        }
    }
}