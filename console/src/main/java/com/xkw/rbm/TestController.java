package com.xkw.rbm;

import com.xkw.rbm.user.User;
import com.xkw.rbm.user.support.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UserService userService;

    @GetMapping("test")
    public String test() {
        return "hello world wangwei!";
    }

    @GetMapping("/users")
    public User addUser() {
        User user = new User();
        user.setUserName("王维");
        user.setPassword("password");

        return userService.add(user);
    }
}
