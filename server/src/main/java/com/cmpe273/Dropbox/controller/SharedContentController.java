package com.cmpe273.Dropbox.controller;

import com.cmpe273.Dropbox.entity.Content;
import com.cmpe273.Dropbox.entity.SharedContent;
import com.cmpe273.Dropbox.entity.User;
import com.cmpe273.Dropbox.service.SharedContentService;
import com.cmpe273.Dropbox.service.UserService;
import com.sun.net.httpserver.Authenticator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
@CrossOrigin(origins="*")
@RequestMapping(path="/sharedContent")
public class SharedContentController {
    @Autowired
    private SharedContentService sharedContentService;
    @PostMapping(path = "/share", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> shareContent(@RequestBody SharedContent sharedContent){
        if(sharedContentService.modifySharedContent(sharedContent))
            return new ResponseEntity<Authenticator.Success>(HttpStatus.CREATED);
        else
            return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(path = "/{sharedToId}")
    public @ResponseBody Iterable<Content> getSharedContents(@PathVariable String sharedToId){
        return sharedContentService.getSharedContent(Integer.parseInt(sharedToId));
    }
}
