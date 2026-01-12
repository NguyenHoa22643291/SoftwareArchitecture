package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // Tên hàng đợi (Queue)
    private static final String QUEUE_NAME = "lab_chat_queue";

    // 1. Gửi tin nhắn (PUSH)
    // URL: http://localhost:8080/chat/send?user=Lan&message=Hello
    @PostMapping("/send")
    public String sendMessage(@RequestParam String user, @RequestParam String message) {
        String content = user + ": " + message;

        // Đẩy vào hàng đợi
        redisTemplate.opsForList().leftPush(QUEUE_NAME, content);

        return "Đã gửi tin: " + content;
    }

    // 2. Nhận tin nhắn (READ)
    // URL: http://localhost:8080/chat/read
    @GetMapping("/read")
    public String readMessage() {
        // Lấy ra khỏi hàng đợi
        String msg = redisTemplate.opsForList().rightPop(QUEUE_NAME);

        if (msg == null) {
            return "Hết tin nhắn rồi!";
        }
        return "Nội dung nhận được: " + msg;
    }
}