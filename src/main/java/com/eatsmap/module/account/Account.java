package com.eatsmap.module.account;

import com.eatsmap.module.account.dto.SignUpRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : ryan
 * @version : 1.0.0
 * @package : com.eatsmap.module.account
 * @name : Account.java
 * @date : 2021/12/17 1:43 오후
 * @modifyed :
 **/
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "account_seq", sequenceName = "account_seq", initialValue = 1001)
public class Account {
    @Id
    @GeneratedValue(generator = "account_seq")
    @Column(name = "account_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;

    @Column(unique = true)
    private String phone;

    private boolean exited;
    private LocalDateTime exitedAt;

    private LocalDateTime joinedAt;
    private LocalDateTime passwordModifiedAt;
    private LocalDateTime lastLoginAt;

    public static Account createAccount(SignUpRequest request, String encodedPassword) {
        return Account.builder()
                .accountType(request.getAccountType())
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .phone(request.getPhone())
                .build();
    }


}
