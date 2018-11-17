package com.weatherly.demo.repositories;

import com.weatherly.demo.entities.User;
import com.weatherly.demo.entities.UserLocation;
import com.weatherly.demo.services.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Locale;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {


    @Query(value = "SELECT locations FROM user WHERE id = :userId", nativeQuery = true)
    List<String> findLocationsById(@Param("userId") Integer userId);

    List<User> findAllByUserId(String id);

    User findByUserId(String id);
}
