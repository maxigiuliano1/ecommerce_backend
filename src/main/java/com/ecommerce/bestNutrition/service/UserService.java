package com.ecommerce.bestNutrition.service;

import com.ecommerce.bestNutrition.exception.UserException;
import com.ecommerce.bestNutrition.model.User;

public interface UserService {
    User findUserById(Long userId) throws UserException;
    User findUserProfileByJwt(String jwt) throws UserException;
}
