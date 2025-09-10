package com.fefe.user_service.service;

import com.fefe.user_service.event.UserRegisteredEvent;
import com.fefe.user_service.exception.EmailAlreadyExistsException;
import com.fefe.user_service.exception.UserNotFoundException;
import com.fefe.user_service.model.CreateOrUpdateUserRequest;
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
    private KafkaProducerService kafkaProducerService;

    public List<CreateUserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::userToCreateUserResponse)
                .toList();
    }


    @Transactional
    public CreateUserResponse createUser(CreateOrUpdateUserRequest request){
        String lowerCaseEmail = request.getEmail().toLowerCase();
        if(userRepository.existsByEmail(lowerCaseEmail)){
            throw new EmailAlreadyExistsException(lowerCaseEmail);
        }

        User user = userMapper.createUserRequestToUser(request);
        User saved = userRepository.save(user);

        UserRegisteredEvent event = new UserRegisteredEvent(
                saved.getId(),
                saved.getEmail(),
                saved.getName(),
                saved.getSurname(),
                saved.getCreatedAt(),
                saved.getLoyaltyCardNumber()
        );
        kafkaProducerService.publishUserRegistered(event);

        return userMapper.userToCreateUserResponse(saved);
    }

    public CreateUserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.userToCreateUserResponse(user);
    }

    @Transactional
    public CreateUserResponse updateUser(CreateOrUpdateUserRequest request, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        String lowerCaseEmail = request.getEmail().toLowerCase();
        if(!user.getEmail().equalsIgnoreCase(lowerCaseEmail) && userRepository.existsByEmail(lowerCaseEmail)){
            throw new EmailAlreadyExistsException(lowerCaseEmail);
        }

        updateUserFields(request, user);

        User saved = userRepository.save(user);
        return userMapper.userToCreateUserResponse(saved);
    }

    private static void updateUserFields(CreateOrUpdateUserRequest request, User user) {
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail().toLowerCase());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setMarketingAccepted(request.getMarketingAccepted());
    }
}
