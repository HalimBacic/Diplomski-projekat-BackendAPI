package backend.multidbapi.multidbapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import backend.multidbapi.multidbapi.dbmodels.User;
import backend.multidbapi.multidbapi.models.LoginRequest;
import backend.multidbapi.multidbapi.models.RegisterRequest;
import backend.multidbapi.multidbapi.models.exceptions.ServerException;
import backend.multidbapi.multidbapi.security.JwtTokenProvider;
import backend.multidbapi.multidbapi.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService) {
        service = userService;
    }

    @SuppressWarnings("null")
    @PostMapping("register")
    public ResponseEntity<User> RegisterUser(@RequestBody RegisterRequest request) {
        try {
            User user = service.RegisterUser(request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ServerException ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("login")
    public ResponseEntity<?> LoginUser(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.Username,
                        request.Password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.createToken(authentication);
        return ResponseEntity.ok(jwt);
    }
}
