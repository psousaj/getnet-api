package com.psousaj.getnetapi.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.psousaj.getnetapi.user.UserRepository;
import com.psousaj.getnetapi.user.entities.User;
import com.psousaj.getnetapi.utils.ErrorResponse;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public List<User> list() {
        return repository.findAll();
    }

    public ResponseEntity<?> create(User user) {
        Optional<User> userExists = repository.findByUsername(user.getUsername());

        if (userExists.isPresent()) {
            var status = HttpStatus.NOT_ACCEPTABLE;
            return ResponseEntity.status(status)
                    .body(new ErrorResponse(status.value(), "Username", "Usuário já existe"));
        }

        return ResponseEntity.ok(repository.save(user));
    }
}
