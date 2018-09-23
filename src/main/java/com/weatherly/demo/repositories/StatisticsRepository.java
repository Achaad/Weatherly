package com.weatherly.demo.repositories;

import com.weatherly.demo.entities.Statistics;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete



public interface StatisticsRepository extends CrudRepository<Statistics, Integer> {


    //Returns all statistics that used certain browser ordered by date
    List<Statistics> findByBrowserOrderByDateDesc(String browser);


    //Returns all statistics that used certain os ordered by date.
    List<Statistics> findByOsOrderByDateDesc(String os);

    int countByBrowser(String browser);


    int countByOs(String os);

}
