package ru.oogis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.oogis.entity.User;
import ru.oogis.services.UserService;

import java.security.Principal;

@RestController
public class MyController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }




    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/auth")
    public String pageForAuthenticatedUsers(Principal principal) {
        return "secured page" + principal.getName();
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/admin")
    public String admin() {
        return "ADMINNNNN";
    }

    @GetMapping("/role")
    public String role(Principal principal){
        User user = userService.findByUsername(principal.getName());
        return user.getRoles().toString();
    }
}
