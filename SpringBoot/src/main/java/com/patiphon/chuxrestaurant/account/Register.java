package com.patiphon.chuxrestaurant.account;

import com.patiphon.chuxrestaurant.DTO.RegisterDTO;
import com.patiphon.chuxrestaurant.database.MySQLConnector;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Register {
    @PostMapping(path = "/register")
    public Map<String, Object> _register(
            @RequestBody RegisterDTO newUser
            ) {
        Map<String, Object> res = new HashMap<>();
        try{
            Connection connection = MySQLConnector.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO user (first_name, last_name, phone_no, email, passwd, getReminders) " +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            pstm.setString(1, newUser.getFn());
            pstm.setString(2, newUser.getLn());
            pstm.setString(3, newUser.getPhone());
            pstm.setString(4, newUser.getEm());
            pstm.setString(5, newUser.getPasswd());
            pstm.setBoolean(6, newUser.getGetRmd());
            pstm.execute();
            res.put("isLogin", true);
        } catch (Exception e) {
            res.put("isLogin", false);
            if (e instanceof SQLIntegrityConstraintViolationException) {
                if (e.getMessage().contains("user_phone_no_uindex")) {
                    res.put("text", "This phone number is already registered :(");
                } else if(e.getMessage().contains("user_email_uindex")) {
                    res.put("text", "This email is already registered :(");
                }
            } else {
                e.printStackTrace();
                res.put("text", "MySQL error :(");
            }
        }
        return res;
    }
}
