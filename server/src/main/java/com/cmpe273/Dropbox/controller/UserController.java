package com.cmpe273.Dropbox.controller;

import com.cmpe273.Dropbox.entity.User;
import com.cmpe273.Dropbox.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins="*")
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/signUp", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        if(userService.getUserByEmail(user.getEmail())==null){
        String saltedPassword = user.getEmail() + user.getPassword();
        String hashedPassword = generateHash(saltedPassword);
        user.setPassword(hashedPassword);
        userService.modifyUser(user);
        return new ResponseEntity(null, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
    }

    @PostMapping(path = "/login", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody String user, HttpSession session){
        JSONObject jsonObject = new JSONObject(user);
        String email=jsonObject.getString("email");
        String password = jsonObject.getString("password");
        String saltedPassword = email + password;
        String hashedPassword = generateHash(saltedPassword);
        session.setAttribute("email",jsonObject.getString("email"));
        return new ResponseEntity(userService.getUserByEmailAndPassword(email,hashedPassword),HttpStatus.OK);
    }
    @PostMapping(path ="/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> logout(HttpSession session){
        session.invalidate();
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }
    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }

        return hash.toString();
    }
}
