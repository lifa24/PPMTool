package io.lifafa.ppmtool.services;

import io.lifafa.ppmtool.domain.User;
import io.lifafa.ppmtool.repositories.UserRepository;
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
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        //Username has to unique(exception)
        //make sure that password and confirmPassword match
        //we don't persist or show the confirmPassword
        return userRepository.save(newUser);
    }
}
