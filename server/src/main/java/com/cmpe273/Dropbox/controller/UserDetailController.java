//package com.cmpe273.Dropbox.controller;
//
//import com.cmpe273.Dropbox.entity.UserDetailsT;
//import com.cmpe273.Dropbox.service.UserDetailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@CrossOrigin(origins="*")
//@RequestMapping(path="/userDetail")
//public class UserDetailController {
//    @Autowired
//    private UserDetailService userDetailService;
//
//    @PostMapping(path = "/update", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> modifyUserDetail(@RequestBody UserDetailsT userDetail) {
//        userDetailService.modifyUser(userDetail);
//        return new ResponseEntity(null, HttpStatus.OK);
//    }
//    @GetMapping(path = "/{userId}")
//    public @ResponseBody UserDetailsT getUserDetail(@PathVariable String id){
//        return userDetailService.getDetail(Integer.parseInt(id));
//    }
//}
