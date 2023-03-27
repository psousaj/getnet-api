package com.psousaj.getnetapi.user.dto;

import java.util.List;
import java.util.UUID;

import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class CreateUserRoleDto {

    private UUID idUser;
    @Nonnull
    private String username;
    private List<Long> idsRoles;

}