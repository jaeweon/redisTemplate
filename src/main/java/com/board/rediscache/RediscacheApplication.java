package com.board.rediscache;

import com.board.rediscache.domain.User;
import com.board.rediscache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class RediscacheApplication implements ApplicationRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(RediscacheApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(User.builder().name("1").email("1@naver.com").build());
        userRepository.save(User.builder().name("2").email("2@naver.com").build());
        userRepository.save(User.builder().name("3").email("3@naver.com").build());
        userRepository.save(User.builder().name("4").email("4@naver.com").build());
    }
}
