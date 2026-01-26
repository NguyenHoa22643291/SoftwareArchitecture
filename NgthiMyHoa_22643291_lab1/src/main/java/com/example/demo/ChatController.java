package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private static final String QUEUE_NAME = "lab_chat_queue";

    // 1. Gửi tin nhắn (Sửa lại để lấy User từ JWT)
    // URL cũ: /chat/send?user=Lan&message=Hello
    // URL mới: /chat/send?message=Hello (User lấy từ Header Authorization)
    @PostMapping("/send")
    public String sendMessage(@RequestHeader("Authorization") String token, @RequestParam String message) {

        // 1. Kiểm tra và cắt bỏ chữ "Bearer " (thường dài 7 ký tự)
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);

            // 2. Validate Token
            if (tokenProvider.validateToken(jwt)) {
                // 3. Lấy tên người dùng từ Token
                String userFromToken = tokenProvider.getUsernameFromJWT(jwt);

                String content = userFromToken + ": " + message;
                redisTemplate.opsForList().leftPush(QUEUE_NAME, content);
                return "Đã gửi tin từ " + userFromToken + ": " + message;
            }
        }

        return "Lỗi: Token không hợp lệ hoặc đã hết hạn!";
    }

    // 2. Nhận tin nhắn (Cũng cần bảo mật bằng Token)
    @GetMapping("/read")
    public String readMessage(@RequestHeader("Authorization") String token) {

        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);

            if (tokenProvider.validateToken(jwt)) {
                String msg = redisTemplate.opsForList().rightPop(QUEUE_NAME);
                if (msg == null) {
                    return "Hàng đợi trống!";
                }
                return "Nội dung nhận được: " + msg;
            }
        }

        return "Lỗi: Bạn không có quyền đọc tin nhắn!";
    }
}