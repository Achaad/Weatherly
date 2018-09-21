package com.weatherly.demo.repositories;

import com.weatherly.demo.entities.Statistics;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface StatisticsRepository extends CrudRepository<Statistics, Integer> {




}
