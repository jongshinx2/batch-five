package com.example.springbatchdownversion.infrastructure.mappers;

import com.example.springbatchdownversion.domain.User;

import java.util.List;

public interface UserMapper {

    List<User> findAll();
}
