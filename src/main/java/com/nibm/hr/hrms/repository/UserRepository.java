package com.nibm.hr.hrms.repository;

import com.nibm.hr.hrms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // This method is essential for Spring Security to find a user by their username
    User findByUsername(String username);
}
