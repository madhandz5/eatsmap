package com.eatsmap.infra.mail.handler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class EmailHandler {

    @PostMapping("mail")
    public String mailTemplate(@RequestParam Map<String,String> template, Model model) {
        model.addAllAttributes(template);
        return "mail-template/" + template.get("mail-template");    //front-side?
    }
}
