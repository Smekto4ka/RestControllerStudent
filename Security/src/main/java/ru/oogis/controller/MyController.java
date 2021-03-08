package ru.oogis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MyController {

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/auth")
    public String pageForAuthenticatedUsers(Principal principal) {
        return "secured page" + principal.getName();
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
