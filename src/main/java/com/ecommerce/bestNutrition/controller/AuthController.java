package com.ecommerce.bestNutrition.controller;

import com.ecommerce.bestNutrition.config.JwtProvider;
import com.ecommerce.bestNutrition.exception.UserException;
import com.ecommerce.bestNutrition.model.User;
import com.ecommerce.bestNutrition.repository.UserRepository;
import com.ecommerce.bestNutrition.request.LoginRequest;
import com.ecommerce.bestNutrition.response.AuthResponse;
import com.ecommerce.bestNutrition.service.impl.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private UserDetailsServiceImpl userDetailsService;
    private PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, UserDetailsServiceImpl userDetailsService,
                          PasswordEncoder passwordEncoder, JwtProvider jwtProvider){
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String userName = user.getUsername();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String role = user.getRole();

        User isExistUsername = userRepository.findByUsername(userName);

        if(isExistUsername!= null){
            throw new UserException("username ir al ready used with another account");
        }

        User createdUser = new User();
        createdUser.setUsername(userName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullName(fullName);
        createdUser.setRole(role);

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getUsername(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("signUp Success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("login Success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
    }
}
