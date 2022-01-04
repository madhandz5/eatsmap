package com.eatsmap.infra.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eatsmap.infra.common.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProps jwtProps;
    private final JWTVerifier jwtVerifier;

    public String decodeJwt(String jwtToken) throws AuthenticationException {
        DecodedJWT decodedJWT;

        try {
            decodedJWT = jwtVerifier.verify(jwtToken);
        } catch (TokenExpiredException te) {
            throw new UsernameNotFoundException(ErrorCode.JWT_TOKEN_EXPIRED.getErrorString());
        } catch (JWTVerificationException ve) {
            throw new UsernameNotFoundException(ErrorCode.JWT_VERIFICATION_FAIL.getErrorString());
        } catch (Exception e) {
            throw new UsernameNotFoundException(ErrorCode.JWT_EXCEPTION_FAIL.getErrorString());
        }
        return decodedJWT.getClaim(jwtProps.getClaimId()).asString();
    }

    public String encodeJwt(String email) {
        return JWT.create()
                .withIssuer(jwtProps.getIssur())
                .withClaim(jwtProps.getClaimId(), email)
                .withExpiresAt(Date.from(ZonedDateTime.now(ZoneId.systemDefault()).plusDays(1).toInstant())) // 하루 설정
                .sign(Algorithm.HMAC256(jwtProps.getSecretkey()));
    }
    //    FEEDBACK : 이런 키들은 노출되면 안되는 값이라서 코드에 작성하기 보다는 application.yml 같은곳에다가 작성하고, 불러와서 사용합니다. PROPS로 정리된 쪽 보세요!

//    private static final Key key = Keys.hmacShaKeyFor("MyNicknameIsEatsmapAndNameIsEatsmap".getBytes(StandardCharsets.UTF_8));
    //토큰 유효시간
//    private static final long JWT_EXPIRATION_MS = System.currentTimeMillis() + 1000 * 60 * 60 * 10; //10h

    // Jwt 토큰에서 아이디 추출
//    public String getUserEmailFromJWT(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//

    //    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public String extractUserEmail(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    user 일치 +
//    토큰 유효시간
//    검증
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String email = extractUserEmail(token);
//        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    public String generateToken(String userName) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userName);
//    }
//
//    public String createToken(Map<String, Object> claims, String subject) { //claims : token으로 만들 값, 사용자정보
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(JWT_EXPIRATION_MS))  //payload
//                .setExpiration(Date.from(ZonedDateTime.now(ZoneId.systemDefault()).plusDays(1).toInstant()))  //payload
//                .signWith(key, SignatureAlgorithm.HS256) //signature
//                .compact();
//    }
//
//
}
