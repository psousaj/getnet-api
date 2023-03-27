package com.psousaj.getnetapi.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psousaj.getnetapi.user.dto.CreateUserRoleDto;
import com.psousaj.getnetapi.user.entities.Role;
import com.psousaj.getnetapi.user.entities.User;
import com.psousaj.getnetapi.user.service.CreateUserRoleService;
import com.psousaj.getnetapi.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    CreateUserRoleService roleService;

    @GetMapping
    public List<User> list() {
        return service.list();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody User user) {
        return service.create(user);
    }

    @PostMapping("/role/set")
    public ResponseEntity<?> createRole(@RequestBody CreateUserRoleDto userRole) {
        return roleService.set(userRole);
    }

    @PostMapping("/role/create")
    public Role createRole(@RequestBody Role role) {
        return roleService.create(role);
    }
}
