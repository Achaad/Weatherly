package com.weatherly.demo.repositories;

import com.weatherly.demo.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT locations FROM user WHERE id = :userId", nativeQuery = true)
    List<String> findLocationsById(@Param("userId") Integer userId);

    List<User> findAllByUserId(Integer id);

    User findByUserId(Integer id);
}
