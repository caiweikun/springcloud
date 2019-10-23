package com.yikun.springcloud.actuator.service;

import com.yikun.springcloud.actuator.dao.UserDao;
import com.yikun.springcloud.actuator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userRepository;

    public User findUserByName(String username){
        return userRepository.findByUserName(username);
    }

    public List<User> findAll(){
       return userRepository.findAll();
    }

    public User saveUser(User user){
      return  userRepository.save(user);
    }

    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

    public User updateUser(User user){
       return userRepository.saveAndFlush(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}

