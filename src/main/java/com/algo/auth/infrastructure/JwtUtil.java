package com.algo.auth.infrastructure;

import com.algo.auth.domain.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class JwtUtil {

  private static final long accessTokenValidity = 60;
  private static final String TOKEN_HEADER = "Authorization";
  private static final String TOKEN_PREFIX = "Bearer ";

  public static final String TOKEN_TYPE = "JWT";
  public static final String TOKEN_ISSUER = "order-api";
  public static final String TOKEN_AUDIENCE = "order-app";

  private final String secret_key = "mysecretkey";
  private final JwtParser jwtParser;

  public JwtUtil() {
    this.jwtParser = Jwts.parser().setSigningKey(secret_key);
  }

  public String createToken(UserInfo user) {
    List<String> roles = new ArrayList<>();
    roles.add(user.getRole());
    return Jwts.builder()
        .setHeaderParam("typ", TOKEN_TYPE)
        .signWith(SignatureAlgorithm.HS256, secret_key)
        .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(accessTokenValidity).toInstant()))
        .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
        .setId(UUID.randomUUID().toString())
        .setIssuer(TOKEN_ISSUER)
        .setAudience(TOKEN_AUDIENCE)
        .setSubject(user.getEmail())
        .claim("rol", roles)
        .claim("name", user.getUserName())
        .claim("preferred_username", user.getEmail())
        .claim("email", user.getEmail())
        .compact();

  }

  private Claims parseJwtClaims(String token) {
    return jwtParser.parseClaimsJws(token).getBody();
  }

  public Claims resolveClaims(HttpServletRequest req) {
    try {
      String token = resolveToken(req);
      if (token != null) {
        return parseJwtClaims(token);
      }
      return null;
    } catch (ExpiredJwtException ex) {
      req.setAttribute("expired", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      req.setAttribute("invalid", ex.getMessage());
      throw ex;
    }
  }

  public String resolveToken(HttpServletRequest request) {

    String bearerToken = request.getHeader(TOKEN_HEADER);
    if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
      return bearerToken.substring(TOKEN_PREFIX.length());
    }
    return null;
  }

  public boolean validateClaims(Claims claims) throws AuthenticationException {
    try {
      return claims.getExpiration().after(new Date());
    } catch (Exception e) {
      throw e;
    }
  }

  public String getEmail(HttpServletRequest request) {
    Claims claims = resolveClaims(request);
    return claims.getSubject();
  }

  private List<String> getRoles(Claims claims) {
    return (List<String>) claims.get("roles");
  }
}
