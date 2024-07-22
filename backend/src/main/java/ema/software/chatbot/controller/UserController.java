package ema.software.chatbot.controller;
import ema.software.chatbot.entity.User;
import ema.software.chatbot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        log.info("UserController, register method debug: " + user.toString());
        return userService.registerUser(user.getUsername(), user.getPassword());
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.authenticate(user.getUsername(), user.getPassword());
    }
}
