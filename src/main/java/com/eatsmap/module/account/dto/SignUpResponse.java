package com.eatsmap.module.account.dto;

import com.eatsmap.module.account.AccountRole;
import com.eatsmap.module.account.AccountType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignUpResponse {

    private Long id;

    private AccountType accountType;

    private AccountRole accountRole;

    private String email;
    private String name;
    private String nickname;

    private LocalDateTime joinedAt;

}
