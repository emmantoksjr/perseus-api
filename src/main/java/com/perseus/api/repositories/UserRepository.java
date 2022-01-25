package com.perseus.api.repositories;

import com.perseus.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM User as u WHERE u.firstName=:name or u.lastName=:name")
    User findByName(String name);
}