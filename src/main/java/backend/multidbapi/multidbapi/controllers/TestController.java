package backend.multidbapi.multidbapi.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String testEndpoint(Authentication authentication) {
        return "Hello, " + authentication.getName() + "! This is a protected endpoint.";
    }
}
