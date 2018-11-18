package com.weatherly.demo.entities;

import org.apache.commons.collections4.list.SetUniqueList;

import javax.persistence.*;
import java.util.*;

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
    private List<UserLocation> locations = new ArrayList<>();

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

    public LinkedHashSet<UserLocation> getLocations() {
        return new LinkedHashSet<>(locations);
    }

    public void setLocations(List<UserLocation> locations) {
        this.locations = new ArrayList<>(locations);
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
