package com.fefe.user_service.service;

import com.fefe.user_service.exception.EmailAlreadyExistsException;
import com.fefe.user_service.exception.UserNotFoundException;
import com.fefe.user_service.model.CreateUserRequest;
import com.fefe.user_service.model.CreateUserResponse;
import com.fefe.user_service.model.User;
import com.fefe.user_service.repository.UserRepository;
import com.fefe.user_service.util.mapstruct.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public List<CreateUserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::userToCreateUserResponse)
                .toList();
    }


    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request){
        String lowerCaseEmail = request.getEmail().toLowerCase();
        if(userRepository.existsByEmail(lowerCaseEmail)){
            throw new EmailAlreadyExistsException(lowerCaseEmail);
        }

        User user = userMapper.createUserRequestToUser(request);

        User saved = userRepository.save(user);
        return userMapper.userToCreateUserResponse(saved);
    }

    public CreateUserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.userToCreateUserResponse(user);
    }
}
