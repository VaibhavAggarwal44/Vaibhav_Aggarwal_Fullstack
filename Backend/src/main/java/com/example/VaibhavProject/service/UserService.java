package com.example.VaibhavProject.service;

import com.example.VaibhavProject.model.User;
import com.example.VaibhavProject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    /**
     * Checks if a user with given username already exists or not
     */
    public boolean Checker(String id){
        return userRepo.existsById(id);
    }

    /**
     * Returns a list containing all users
     */
    public Iterable<User> getUsers(){
        return userRepo.findAll();
    }

    /**
     * Saves a user in the database
     */
    public User insertUser(User user){
        return userRepo.save(user);
    }

    /**
     * Returns user object with given username(id)
     */
    public User findById(String id){
        return userRepo.findById(id).get();
    }

}
