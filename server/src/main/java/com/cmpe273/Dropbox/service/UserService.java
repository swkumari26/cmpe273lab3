package com.cmpe273.Dropbox.service;

import com.cmpe273.Dropbox.entity.User;
import com.cmpe273.Dropbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void modifyUser(User user){
        userRepository.save(user);
    }
    public User getUserById(Long id){
        return userRepository.findOne(id);
    }
    public List<User> getUserByEmailAndPassword(String email,String password){
        return userRepository.findByEmailAndPassword(email,password);
    }
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserByEmail(String email){return userRepository.findByEmail(email);}
}
