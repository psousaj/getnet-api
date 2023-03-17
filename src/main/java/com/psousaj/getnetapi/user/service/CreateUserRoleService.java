package com.psousaj.getnetapi.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.psousaj.getnetapi.user.RoleRepository;
import com.psousaj.getnetapi.user.UserRepository;
import com.psousaj.getnetapi.user.dto.CreateUserRoleDto;
import com.psousaj.getnetapi.user.entities.Role;
import com.psousaj.getnetapi.user.entities.User;
import com.psousaj.getnetapi.utils.ErrorResponse;

@Service
public class CreateUserRoleService {

    @Autowired
    UserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    public ResponseEntity<?> set(CreateUserRoleDto userRole) {
        Optional<User> userExists = repository.findByUsername(userRole.getUsername());
        List<Role> roles = new ArrayList<>();

        if (userExists.isEmpty()) {
            var status = HttpStatus.NOT_FOUND;
            return ResponseEntity.status(status)
                    .body(new ErrorResponse(status.value(), "Username", "Usuário Não encontrado"));
        }

        var user = userExists.get();
        roles = userRole.getIdsRoles().stream().map(role -> {
            return new Role(role);
        }).collect(Collectors.toList());

        user.setUserRoles(roles);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(repository.save(user));
    }

    public Role create(Role role) {
        return roleRepository.save(role);
    }
}
