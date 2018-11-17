package com.weatherly.demo.services;

import com.weatherly.demo.entities.User;
import com.weatherly.demo.entities.UserLocation;
import com.weatherly.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> listAllByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void addLocation(String location, String userId) {
        User user = userRepository.findByUserId(userId);
        UserLocation userLocation = new UserLocation(location);
        user.addLoaction(userLocation);
        userRepository.save(user);
    }
}
