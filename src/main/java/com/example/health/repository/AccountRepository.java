package com.example.health.repository;

import com.example.health.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    Account findByUsernameAndRole(String username, int role);


}
