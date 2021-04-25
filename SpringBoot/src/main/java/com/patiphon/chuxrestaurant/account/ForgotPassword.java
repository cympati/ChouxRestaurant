package com.patiphon.chuxrestaurant.account;

import com.patiphon.chuxrestaurant.database.MySQLConnector;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class ForgotPassword {
    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping(path = "/forgot")
    public Map<String, Object> _forgot(@RequestParam String email) {
        Map<String, Object> res = new HashMap<>();
        try{
            Connection connection = MySQLConnector.getConnection();

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO reset_passwd (verify_cd, expire_tm, id_user) " +
                    "SELECT ? as verify_cd, ? as expire_tm, id_user from user WHERE email = ?", Statement.RETURN_GENERATED_KEYS);

            // Random
            String generatedString = RandomStringUtils.randomAlphanumeric(6);
            pstm.setString(1, generatedString);

            Timestamp expire_tm = new Timestamp(new java.util.Date().getTime() + (15 * 60 * 1000)); // 15 minutes
            pstm.setTimestamp(2, expire_tm);

            pstm.setString(3, email);
            pstm.execute();

            // Check Id in "reset_passwd" table
            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    res.put("id_reset",generatedKeys.getInt(1));
                    SimpleMailMessage msg = new SimpleMailMessage();
                    msg.setTo(email);
                    msg.setFrom("chuxreataurant@patiphon.cf");
                    msg.setSubject("Chuxrestaurant password reset token");
                    msg.setText("Your verification code is " + generatedString);
                    javaMailSender.send(msg);
                    res.put("success", true);
                    res.put("text", "Please check your email for verification code :)");
                }
                else {
                    res.put("success", false);
                    res.put("text", "Creating user failed, no ID obtained :(");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Something Wrong :(");
        }
        return res;
    }

    @PostMapping(path = "/reset")
    public Map<String, Object> _reset(@RequestParam int id_reset, @RequestParam String new_passwd, @RequestParam String verify) {
        Map<String, Object> res = new HashMap<>();
        try{
            Connection conn = MySQLConnector.getConnection();
            PreparedStatement pstm = conn.prepareStatement("SELECT id_user FROM reset_passwd WHERE ? = verify_cd AND ? = id_reset AND expire_tm > ?");
            pstm.setString(1, verify);
            pstm.setInt(2, id_reset);
            pstm.setTimestamp(3, new Timestamp(new Date().getTime()));
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                PreparedStatement user = conn.prepareStatement("UPDATE user SET passwd = ? WHERE id_user = ?");

                user.setInt(2, rs.getInt("id_user"));
                user.setString(1, new_passwd);

                user.execute();

                res.put("success", true);
                res.put("text", "Your password is changed :)");
            } else {
                res.put("success", false);
                res.put("text", "Something Wrong, no ID obtained or your verification code is incorrect :(");
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Something Wrong :(");
        }

        return res;
    }

}
