    package com.fitness.user_service.service;

    import com.fitness.user_service.dto.RegisterRequest;
    import com.fitness.user_service.dto.UserResponse;
    import com.fitness.user_service.model.User;
    import com.fitness.user_service.repository.UserRepository;
    import jakarta.validation.Valid;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    @Service
    @Slf4j
    public class UserService {

        @Autowired
        private UserRepository userRepository;

        public UserResponse getUserProfile(String userId) {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setEmail(user.getEmail());
            response.setPassword(user.getPassword());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setCreatedAt(user.getCreatedAt());
            response.setUpdatedAt(user.getUpdatedAt());

            return response;

        }

        public UserResponse register(@Valid RegisterRequest request) {

            if (userRepository.existsByEmail(request.getEmail())) {
                User existingUser = userRepository.findByEmail(request.getEmail());
                UserResponse response = new UserResponse();
                response.setId(existingUser.getId());
                response.setEmail(existingUser.getEmail());
                response.setKeycloakId(response.getKeycloakId());
                response.setPassword(existingUser.getPassword());
                response.setFirstName(existingUser.getFirstName());
                response.setLastName(existingUser.getLastName());
                response.setCreatedAt(existingUser.getCreatedAt());
                response.setUpdatedAt(existingUser.getUpdatedAt());
                return response;
            }

            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setKeycloakId(request.getKeycloakId());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());

            User savedUser = userRepository.save(user);
            UserResponse response = new UserResponse();
            response.setId(savedUser.getId());
            response.setKeycloakId(savedUser.getKeycloakId());
            response.setEmail(savedUser.getEmail());
            response.setPassword(savedUser.getPassword());
            response.setFirstName(savedUser.getFirstName());
            response.setLastName(savedUser.getLastName());
            response.setCreatedAt(savedUser.getCreatedAt());
            response.setUpdatedAt(savedUser.getUpdatedAt());

            return response;

        }

        public Boolean existByUserId(String userId) {

            log.info("Calling User Validation API for userId: {}", userId);

            return userRepository.existsByKeycloakId(userId);
        }
    }
