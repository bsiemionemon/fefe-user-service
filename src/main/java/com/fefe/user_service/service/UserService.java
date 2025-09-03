package com.fefe.user_service.service;

import com.fefe.user_service.model.CreateUserRequest;
import com.fefe.user_service.model.CreateUserResponse;
import com.fefe.user_service.model.User;
import com.fefe.user_service.repository.UserRepository;
import com.fefe.user_service.util.mapstruct.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public CreateUserResponse createUser(CreateUserRequest request){
        User user = userMapper.createUserRequestToUser(request);
        User saved = userRepository.save(user);
        return userMapper.userToCreateUserResponse(saved);
    }

//    public CreateUserResponse getUser(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new (HttpStatus.NOT_FOUND, "User not found with id: " + id));
//        return new CreateUserResponse();
//    }
}
