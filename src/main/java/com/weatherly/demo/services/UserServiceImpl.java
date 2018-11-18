package com.weatherly.demo.services;

import com.weatherly.demo.entities.User;
import com.weatherly.demo.entities.UserLocation;
import com.weatherly.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
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
    public Page<UserLocation> listLocationsById(Pageable pageable, Integer id) {
        List<UserLocation> userLocations = new ArrayList<>();
        userLocations.addAll(userRepository.findUserById(id).getLocations());

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = pageSize * currentPage;
        List<UserLocation> list;

        if (userLocations.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, userLocations.size());
            list = userLocations.subList(startItem, toIndex);
        }

        Page<UserLocation> locationPage = new PageImpl<UserLocation>(list, PageRequest.of(currentPage, pageSize), userLocations.size());

        return locationPage;

    }

    @Override
    public void addLocation(String location, String userId) {
        User user = userRepository.findByUserId(userId);
        UserLocation userLocation = new UserLocation(location);
        user.addLoaction(userLocation);
        userRepository.save(user);


    }
}
