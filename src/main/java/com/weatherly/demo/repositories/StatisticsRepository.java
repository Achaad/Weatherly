package com.weatherly.demo.repositories;

import com.weatherly.demo.entities.Statistics;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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


    //Võte aggregeeritud andmete esitamine
    @Query(value = "SELECT * FROM statistics WHERE os='Windows' GROUP BY id", nativeQuery = true)
    List<Statistics> aggregeeritudVote();

}
