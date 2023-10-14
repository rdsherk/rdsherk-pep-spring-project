package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer>{

    /**
     * Query to find an account given a username
     * @param username
     * @return
     */
    Optional<Account> findAccountByUsername(String username);
}
