package com.edem.medlink.repository;

import com.edem.medlink.entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);

    @Query("select u from User u where u.email=:email and u.isVerified= true ")
    Optional<User> findUserByEmailAndVerified(String email);
}
