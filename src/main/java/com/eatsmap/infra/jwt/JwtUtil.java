package com.eatsmap.infra.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
//@Service
// FEEDBACK : 엄밀히 말하면 이아이는 서비스가 아니라서, @Service 보다는 @Component 가 맞습니다.
@RequiredArgsConstructor
public class JwtUtil {

    //    FEEDBACK : 이런 키들은 노출되면 안되는 값이라서 코드에 작성하기 보다는 application.yml 같은곳에다가 작성하고, 불러와서 사용합니다.
    private static final Key key = Keys.hmacShaKeyFor("MyNicknameIsEatsmapAndNameIsEatsmap".getBytes(StandardCharsets.UTF_8));
    //토큰 유효시간
//    private static final long JWT_EXPIRATION_MS = System.currentTimeMillis() + 1000 * 60 * 60 * 10; //10h

    // Jwt 토큰에서 아이디 추출
    public String getUserEmailFromJWT(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //user 일치 + 토큰 유효시간 검증
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractUserEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    public String createToken(Map<String, Object> claims, String subject) { //claims : token으로 만들 값, 사용자정보
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(JWT_EXPIRATION_MS))  //payload
//                FEEDBACK : 이렇게 처리하면 더 간단할것 같아요.
                .setExpiration(Date.from(ZonedDateTime.now(ZoneId.systemDefault()).plusDays(1).toInstant()))  //payload
                .signWith(key, SignatureAlgorithm.HS256) //signature
                .compact();
    }


}
