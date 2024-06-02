package com.example.assigmentthree.controller;

import com.example.assigmentthree.model.UserData;
import com.example.assigmentthree.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignUpController {

    private SignUpService signUpService;


    @Autowired
    public SignUpController(SignUpService signUpService) {
        super();
        this.signUpService = signUpService;
    }

    @PostMapping(value = "/newUser")
    public ResponseEntity<UserData> newUser(@RequestBody UserData userData) {
        if (userData == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<UserData>(signUpService.addNewUser(userData), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getUser/{email}")
    public ResponseEntity<UserData> getUser(@PathVariable("email") String email) {
        return new ResponseEntity<UserData>(signUpService.getUserByEmail(email), HttpStatus.OK);
    }

    @PutMapping(value = "/updateUser/{id}")
    public ResponseEntity<UserData> updateUser(@PathVariable("id") String email, @RequestBody UserData userData) {
        return new ResponseEntity<UserData>(signUpService.updateUser(userData, email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long userId) {
        signUpService.deleteUser(userId);
        return new ResponseEntity<String>("User deleted!", HttpStatus.OK);
    }
}