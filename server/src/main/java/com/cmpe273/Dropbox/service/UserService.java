package com.cmpe273.Dropbox.service;

import com.cmpe273.Dropbox.entity.User;
import com.cmpe273.Dropbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContentService contentService;
    public void modifyUser(User user){
        userRepository.save(user);
        String absolutePath = new File("dropbox").getPath()+'/'+userRepository.findByEmail(user.getEmail()).getId();
        File folderPath = new File(absolutePath);
        folderPath.mkdir();
    }
    public void contentCreatedCount(Integer id){userRepository.contentCreatedCount(id);}
    public void contentDeletedCount(Integer id){userRepository.contentDeletedCount(id);}
    public void contentSharedCount(Integer id){userRepository.contentSharedCount(id);}
    public User getUserById(Integer id){
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
