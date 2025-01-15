package com.board.rediscache.service;

import com.board.rediscache.domain.User;
import com.board.rediscache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, User> redisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    public User getUser(final Long id) {
        var key = "users:%d".formatted(id);

        var cacheUser = objectRedisTemplate.opsForValue().get(key);
        if (cacheUser != null) {
            return (User)cacheUser;
        }

        User user = userRepository.findById(id).orElseThrow();
        redisTemplate.opsForValue().set(key, user);

        return user;
    }
}
