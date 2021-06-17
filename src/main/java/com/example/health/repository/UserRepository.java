package com.example.health.repository;

import com.example.health.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByAccountId(int accountId);
}
