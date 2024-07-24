package backend.multidbapi.multidbapi.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

      private final UserDetailsService userDetailsService;
      private final JwtTokenProvider jwtTokenProvider;
  
      public JwtTokenFilter(@Lazy UserDetailsService userDetailsService,@Lazy JwtTokenProvider jwtTokenProvider) {
          this.userDetailsService = userDetailsService;
          this.jwtTokenProvider = jwtTokenProvider;
      }

      @Override
      protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response,
          FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
    
        String token = jwtTokenProvider.resolveToken(request);
    
        if (token != null && jwtTokenProvider.validateToken(token)) {
    
          UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
    
          UsernamePasswordAuthenticationToken authentication 
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    
        filterChain.doFilter(request, response);
      }
}
