package com.example.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    // Chuỗi bí mật đủ dài (>64 ký tự) để tránh lỗi WeakKeyException
    private final String SECRET_STRING = "NgthiMyHoa_22643291_Day_La_Chuoi_Bi_Mat_Sieu_Dai_De_Pass_Loi_WeakKeyException_HS512";
    private final Key key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    private final long JWT_EXPIRATION = 604800000L; // Token có hiệu lực trong 7 ngày

    // 1. Tạo token từ username
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, key) // Thứ tự: Thuật toán trước, Key sau
                .compact();
    }

    // 2. Lấy username từ token
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // 3. Kiểm tra token có hợp lệ không
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            System.out.println("Lỗi xác thực JWT: " + ex.getMessage());
            return false;
        }
    }
}