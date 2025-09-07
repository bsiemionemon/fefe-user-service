package com.fefe.user_service.controller;

import com.fefe.user_service.model.CreateUserRequest;
import com.fefe.user_service.model.CreateUserResponse;
import com.fefe.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<CreateUserResponse>> getAllUsers() {
            List<CreateUserResponse> users = userService.getAllUsers();
            return users.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateUserResponse> getUser(@PathVariable Long id) {
        CreateUserResponse user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }
}