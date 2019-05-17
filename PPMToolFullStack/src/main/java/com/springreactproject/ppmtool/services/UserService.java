package com.springreactproject.ppmtool.services;

import com.springreactproject.ppmtool.domain.User;
import com.springreactproject.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.springreactproject.ppmtool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            //Username has to be unique(exception)
            newUser.setUsername(newUser.getUsername());

            newUser.setConfirmPassword("");
            //make sure that password and confirm password match
            //we dont persist or show the confirmPassword
            return userRepository.save(newUser);
        }
        catch(Exception e){
            throw new UsernameAlreadyExistsException("Username "+newUser.getUsername()+""+"already exists");
        }

    }
}
