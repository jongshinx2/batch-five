package com.example.springbatchdownversion.infrastructure.repositories;

import com.example.springbatchdownversion.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
}
