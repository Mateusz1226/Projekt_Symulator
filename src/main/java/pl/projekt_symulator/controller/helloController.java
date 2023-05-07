package pl.projekt_symulator.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.projekt_symulator.service.UserService;

@Controller
public class helloController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public helloController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/hello")
    public String sayHello() {
      return  "hello";
    }
}