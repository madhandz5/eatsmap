package com.eatsmap.module.member.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 8, max = 15)
    private String password;

    @NotEmpty
    @Size(min = 8, max = 15)
    private String passwordConfirm;

    @NotEmpty
    private String nickname;
}
