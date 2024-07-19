package com.ecommerce.bestNutrition.service.impl;

import com.ecommerce.bestNutrition.config.JwtProvider;
import com.ecommerce.bestNutrition.exception.UserException;
import com.ecommerce.bestNutrition.model.User;
import com.ecommerce.bestNutrition.repository.UserRepository;
import com.ecommerce.bestNutrition.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider){
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserById(Long id) throws UserException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        throw new UserException("User not found with id: " + id  );
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String username = jwtProvider.getUsernameFromJWT(jwt);
        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new UserException("User not found with username " + username);
        }
        return user;
    }
}
