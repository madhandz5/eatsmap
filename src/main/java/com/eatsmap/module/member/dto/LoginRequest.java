package com.eatsmap.module.member.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
