package com.meghraj.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.meghraj.ppmtool.domain.User;
import com.meghraj.ppmtool.exceptions.UserNameAlreadyExistsException;
import com.meghraj.ppmtool.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public  User saveUser (User newUser){

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());

            //Make sure that password and confirmPassword match
            //We don't persist the confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);

        }catch (Exception e){ //if sql constraint violation comes it will be catched here and custom error response UserNameAlreadyExistsExceptionResponse will be thrown by the ExceptionHandler
            throw new UserNameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists !");

        }

    }

}
