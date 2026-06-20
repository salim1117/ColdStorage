package com.csms.repository;

import com.csms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndIdNot(String username, Long id);
    List<User> findByStatus(Integer status);
    long countByUsernameAndIdNot(String username, Long id);
    long countByUsername(String username);
}
