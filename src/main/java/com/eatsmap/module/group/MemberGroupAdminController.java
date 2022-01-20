package com.eatsmap.module.group;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MemberGroupAdminController {

    private MemberGroupAdminService memberGroupAdminService;
}
