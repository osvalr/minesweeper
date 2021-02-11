package com.osvalr.minesweeper.repository;

import com.osvalr.minesweeper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query
    Optional<User> findByUser(String user);

    @Query
    Optional<User> findByActiveToken(String token);
}
