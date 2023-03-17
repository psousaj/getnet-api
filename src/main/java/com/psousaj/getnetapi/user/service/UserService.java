package com.psousaj.getnetapi.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.psousaj.getnetapi.user.UserRepository;
import com.psousaj.getnetapi.user.entities.User;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public List<User> list() {
        return repository.findAll();
    }

    public ResponseEntity<User> create(User user) {
        return ResponseEntity.ok(repository.save(user));
    }
}
