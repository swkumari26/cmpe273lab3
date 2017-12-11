//package com.cmpe273.Dropbox.service;
//
//import com.cmpe273.Dropbox.entity.UserDetailsT;
//import com.cmpe273.Dropbox.repository.UserDetailRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class UserDetailService {
//    @Autowired
//    private UserDetailRepository userDetailRepository;
//    public void modifyUser(UserDetailsT userDetail){
//        userDetailRepository.save(userDetail);
//    }
//    public UserDetailsT getDetail(Integer userId){
//        return userDetailRepository.findByUserId(userId);
//    }
//}
