package com.weatherly.demo.services;

import com.weatherly.demo.entities.User;
import com.weatherly.demo.entities.UserLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<User> listAllByPage(Pageable page);

    public void addLocation(String location, String userId);
}
