package com.example.springbatchdownversion.infrastructure.batch;

import com.example.springbatchdownversion.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class UserProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) throws Exception {

        user.setName(user.getName().toUpperCase());

        log.info("Processing user: {}", user.getName());
        return user;
    }
}
