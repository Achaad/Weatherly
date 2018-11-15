package com.weatherly.demo.services;

import com.weatherly.demo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> listAllByPage(Pageable page);
}
