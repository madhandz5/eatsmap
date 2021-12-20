package com.eatsmap.module.account.dto;

import com.eatsmap.module.account.MemberType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class SignUpRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordConfirm;

    @NotEmpty
    private String name;

    @NotEmpty
    private String nickname;
}
