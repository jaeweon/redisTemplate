package com.board.rediscache.service;

import com.board.rediscache.domain.RedisHashUser;
import com.board.rediscache.domain.User;
import com.board.rediscache.repository.RedisHashUserRepository;
import com.board.rediscache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.board.rediscache.config.CacheConfig.CACHE1;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, User> redisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;
    private final RedisHashUserRepository redisHashUserRepository;


    public User getUser(final Long id) {
        var key = "users:%d".formatted(id);

        var cacheUser = objectRedisTemplate.opsForValue().get(key);
        if (cacheUser != null) {
            return (User)cacheUser;
        }

        User user = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(20));

        return user;
    }

    public RedisHashUser getHashUser(final Long id) {
        var cashedUser = redisHashUserRepository.findById(id).orElseGet(() -> {
            User user = userRepository.findById(id).orElseThrow();
            return redisHashUserRepository.save(RedisHashUser.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .createdAt(user.getCreatedAt())
                            .updatedAt(user.getUpdatedAt())
                            .build());
        });
        return cashedUser;
    }

    @Cacheable(cacheNames = CACHE1, key = "'user:' + #id")
    public User getCacheUser(final Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
