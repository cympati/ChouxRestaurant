package com.patiphon.chuxrestaurant.test;


import com.patiphon.chuxrestaurant.database.MySQLConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping(path = "/mail")
    public Map<String, Object> _mail() {
        Map<String, Object> res = new HashMap<>();
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo("cympati@gmail.com");
            msg.setFrom("chuxreataurant@patiphon.cf");
            msg.setSubject("Testing from Spring Boot");
            msg.setText("Hello World \n Spring Boot Email");
            javaMailSender.send(msg);
            res.put("success" , true);
        return res;
    }
}
