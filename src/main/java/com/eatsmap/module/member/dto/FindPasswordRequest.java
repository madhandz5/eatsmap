package com.eatsmap.module.member.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class FindPasswordRequest {

    @Email
    @NotEmpty
    private String email;
}
