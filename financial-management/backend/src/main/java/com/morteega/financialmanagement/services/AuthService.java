package com.morteega.financialmanagement.services;


import org.springframework.stereotype.Service;

import com.morteega.financialmanagement.dtos.UserResponseDTO;
import com.morteega.financialmanagement.model.users.User;
import com.morteega.financialmanagement.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public UserResponseDTO login(String email, String password){
        User user= userRepository.findByEmail(email);// En el controler sera donde haga el chequeo de si es null o no para hacer register
        if(this.passwordCheck(password, user.getPassword())==true){
            UserResponseDTO response=this.toDto(user);
            return response;
        }else
            throw new RuntimeException("The password is incorrect");
    }

    public UserResponseDTO registerUser(String email, String password){
        User user = new User(email, passwordEncoder.encode(password), null);
        userRepository.save(user);
        UserResponseDTO response=this.toDto(user);
        return response;
    }
    
    private boolean passwordCheck(String password, String password2){
        return passwordEncoder.matches(password, password2);
    }
    private UserResponseDTO toDto(User user){
        UserResponseDTO response= new UserResponseDTO();
        response.setEmail(user.getEmail());
        response.setId(user.getId());
        return response;
    }
}
