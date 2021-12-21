package com.eatsmap.module.member.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ModifyRequest {

    private String nickname;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordConfirm;

}
