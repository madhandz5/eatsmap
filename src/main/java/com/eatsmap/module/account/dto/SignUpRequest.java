package com.eatsmap.module.account.dto;

import com.eatsmap.module.account.AccountType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SignUpRequest {

    @NotEmpty
    private AccountType accountType;

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
    private String phone;
}
