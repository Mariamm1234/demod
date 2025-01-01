package com.example.demod.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demod.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT COUNT(u) FROM User u WHERE u.userEmail = :userEmail")
    long countUsersByEmail(@Param("userEmail") String userEmail);

    @Query("SELECT u FROM User u WHERE u.userEmail = :userEmail")
    User findUsersByEmail(@Param("userEmail") String userEmail);

    @Query("SELECT u FROM User u WHERE u.user_id = :userId")
    User findUserById(@Param("userId") Integer userId);

}
