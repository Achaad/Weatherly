package com.weatherly.demo.entities;

import com.weatherly.demo.services.Location;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Entity class that represents User DB table
 */
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String userId; // Id that is given to user by service (Google, Facebook etc.)

    private String firstName;

    private String lastName;

    private String fullName;

    private String mail;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_locations", joinColumns = @JoinColumn(name = "id"))
    private Set<UserLocation> locations = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<UserLocation> getLocations() {
        return locations;
    }

    public void setLocations(Set<UserLocation> locations) {
        this.locations = locations;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean addLoaction(UserLocation location) {
        return this.locations.add(location);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        return Objects.equals(this.id, user.id) && Objects.equals(this.userId, user.userId);
    }
}
