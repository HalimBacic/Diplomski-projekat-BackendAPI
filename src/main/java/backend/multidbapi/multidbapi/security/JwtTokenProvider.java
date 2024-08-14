package backend.multidbapi.multidbapi.security;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Component
@Slf4j
public class JwtTokenProvider {
  
  @Value("${jwt.secret-key}")
  private String secretKey;

  public String createToken(Authentication authentication) {
  
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + 3600000);

    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
        .compact();
  }

    public String resolveToken(HttpServletRequest request) {
  
      String bearerToken = request.getHeader("Authorization");
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        return bearerToken.substring(7);
      }
      return null;
    }
  
    // Check if the token is valid and not expired
    public boolean validateToken(String token) {
      
      try {

            Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
            return true;
      } catch (MalformedJwtException ex) {
        log.error("Invalid JWT token");
      } catch (ExpiredJwtException ex) {
        log.error("Expired JWT token");
      } catch (UnsupportedJwtException ex) {
        log.error("Unsupported JWT token");
      } catch (IllegalArgumentException ex) {
        log.error("JWT claims string is empty");
      } catch (SignatureException e) {
        log.error("there is an error with the signature of you token ");
      }
      return false;
    }

    public String getUsername(String token) {
    
        return Jwts.parserBuilder()
            .setSigningKey(secretKey.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
      }
}
