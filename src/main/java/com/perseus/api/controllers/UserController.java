package com.perseus.api.controllers;

import com.perseus.api.dto.requests.AddEmailOrPhoneRequest;
import com.perseus.api.dto.requests.RegisterRequest;
import com.perseus.api.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@Valid @RequestBody RegisterRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser(
            @Nullable @RequestParam Long id,
            @Nullable @RequestParam String name
    ) {
        return userService.getUser(id, name);
    }

    @PostMapping("/user/add-email-phone")
    public ResponseEntity<Object> addEmailOrPhone(@Valid @RequestBody AddEmailOrPhoneRequest request) {
        return userService.addEmailOrPhoneNumber(request);
    }

    @PutMapping("/user/{userId}/phone/{phoneId}/{number}")
    public ResponseEntity<Object> updateUserPhone(
            @PathVariable Long userId,
            @PathVariable Long phoneId,
            @PathVariable String number
            ) {
        return userService.updateUserPhone(userId, phoneId, number);
    }

    @PutMapping("/user/{userId}/email/{emailId}/{mail}")
    public ResponseEntity<Object> updateUserEmail(
            @PathVariable Long userId,
            @PathVariable Long emailId,
            @PathVariable String mail
    ) {
        return userService.updateUserEmail(userId, emailId, mail);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
