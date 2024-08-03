package backend.multidbapi.multidbapi.security;

import java.util.Collection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class JwtTestRunner implements CommandLineRunner {

  private final JwtTokenProvider jwtTokenProvider;

  public JwtTestRunner(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void run(String... args) throws Exception {
    UserDetailsService userDetailsService = new InMemoryUserDetailsManager(
        User.withUsername("testuser").password("{noop}password").roles("USER").build());

    UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");
    String token = jwtTokenProvider.createToken(new Authentication() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
      }

      @Override
      public Object getCredentials() {
        return userDetails.getPassword();
      }

      @Override
      public Object getDetails() {
        return userDetails;
      }

      @Override
      public Object getPrincipal() {
        return userDetails;
      }

      @Override
      public boolean isAuthenticated() {
        return true;
      }

      @Override
      public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
      }

      @Override
      public String getName() {
        return userDetails.getUsername();
      }
    });

    System.out.println("Generated Token: " + token);
    boolean isValid = jwtTokenProvider.validateToken(token);
    System.out.println("Is token valid? " + isValid);
  }
}
