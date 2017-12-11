package com.cmpe273.Dropbox.controller;

import com.cmpe273.Dropbox.entity.Content;
import com.cmpe273.Dropbox.entity.UserGroup;
import com.cmpe273.Dropbox.service.GroupService;
import com.sun.net.httpserver.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@Controller
@CrossOrigin(origins="*")
@RequestMapping(path="/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @PostMapping(path = "/create", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createGroup(@RequestBody UserGroup group){
        if(groupService.createGroup(group))
            return new ResponseEntity<Authenticator.Success>(HttpStatus.CREATED);
        else
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
    }
    @GetMapping(path = "/{id}")
    public @ResponseBody Iterable<Content> getGroups(@PathVariable Integer id){
        return groupService.getGroups(id);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable String id){
            groupService.removeGroup(Integer.parseInt(id));
            return new ResponseEntity(HttpStatus.OK);
    }
}
