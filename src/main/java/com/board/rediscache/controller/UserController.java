package com.board.rediscache.controller;

import com.board.rediscache.domain.RedisHashUser;
import com.board.rediscache.domain.User;
import com.board.rediscache.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getCacheUser(id);
    }

    @GetMapping("redishash-users/{id}")
    public RedisHashUser getHashUser(@PathVariable Long id) {
        return userService.getHashUser(id);
    }
}
