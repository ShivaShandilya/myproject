package com.indigo.service;

import com.indigo.dto.LoginDto;
import com.indigo.dto.PropertyUserDto;
import com.indigo.entity.PropertyUser;
import com.indigo.repository.PropertyUserRepository;

import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service


public class UserService {


    @Autowired

    private PropertyUserRepository userRepository;
@Autowired
    private JWTService jwtService;



    public PropertyUser addUser(PropertyUserDto propertyUserDto){
        PropertyUser user = new PropertyUser();
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setUsername(propertyUserDto.getUsername());
        user.setEmail(propertyUserDto.getEmail());
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(10)));
        user.setUserRole(propertyUserDto.getUserRole());

        PropertyUser savedUser = userRepository.save(user);
        return savedUser;



    }


    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> opUser = userRepository.findByUsername(loginDto.getUsername());
        if(opUser.isPresent()){
            PropertyUser propertyUser = opUser.get();
            if (BCrypt.checkpw(loginDto.getPassword(),propertyUser.getPassword())){
                return  jwtService.generateToken(propertyUser);
            }

        }

        return null;
    }
}
