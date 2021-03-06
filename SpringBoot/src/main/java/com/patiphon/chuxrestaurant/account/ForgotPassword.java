package com.patiphon.chuxrestaurant.account;

import com.patiphon.chuxrestaurant.DTO.LoginDTO;
import com.patiphon.chuxrestaurant.DTO.ResetPasswordDTO;
import com.patiphon.chuxrestaurant.database.MySQLConnector;
import com.patiphon.chuxrestaurant.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, Object> _forgot(@RequestBody LoginDTO info) {
        Map<String, Object> res = new HashMap<>();
        try {

            Connection conn = MySQLConnector.getConnection();

            PreparedStatement check_email = conn.prepareStatement("SELECT id_user FROM user WHERE email = ?");
            check_email.setString(1,info.getEmail());
            ResultSet rs_check_email = check_email.executeQuery();
            if (!rs_check_email.next()){
                res.put("success", false);
                res.put("text", "Email is invalid :(");
                return res;
            }

            PreparedStatement pstm = conn.prepareStatement("INSERT INTO reset_passwd (verify_cd, expire_tm, id_user) " +
                    "SELECT ? as verify_cd, ? as expire_tm, id_user from user WHERE email = ?", Statement.RETURN_GENERATED_KEYS);

            // Random
            String generatedString = RandomStringUtils.randomNumeric(6);
            pstm.setString(1, generatedString);

            Timestamp expire_tm = new Timestamp(new java.util.Date().getTime() + (5 * 60 * 1000)); // 5 minutes
            pstm.setTimestamp(2, expire_tm);
            pstm.setString(3, info.getEmail());
            int rs = pstm.executeUpdate();

            if (rs >= 1) {
                // Check Id in "reset_passwd" table
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        res.put("id_reset", generatedKeys.getInt(1));
                        SimpleMailMessage msg = new SimpleMailMessage();
                        msg.setTo(info.getEmail());
//                            msg.setTo("chancreamz@gmail.com");
                        msg.setFrom("chuxreataurant@patiphon.cf");
                        msg.setSubject("Chuxrestaurant password reset token");
                        msg.setText("Your verification code is " + generatedString);
                        System.out.println("Your verification code is " + generatedString + " send to " + info.getEmail());
//                            javaMailSender.send(msg);
                        res.put("success", true);
                        res.put("text", "Please check your email for verification code :)");
                        return res;
                    } else {
                        res.put("success", false);
                        res.put("text", "Creating user failed, no reset ID obtained :(");
                        return res;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    res.put("success", false);
                    res.put("text", "Something Wrong (generatedKeys) :(");
                }
            }

            res.put("success", false);
            res.put("text", "Creating user failed, no email obtained :(");

        } catch (JwtException e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Token is incorrect :(");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Something Wrong :(");
        }
        return res;
    }

    @PatchMapping(path = "/reset")
    public Map<String, Object> _reset(@RequestBody ResetPasswordDTO info) {
        Map<String, Object> res = new HashMap<>();
        try {
            Connection conn = MySQLConnector.getConnection();
            PreparedStatement pstm = conn.prepareStatement("SELECT id_user FROM reset_passwd WHERE ? = verify_cd AND ? = id_reset AND expire_tm > ?");
            pstm.setString(1, info.getVerify());
            pstm.setInt(2, info.getId_reset());
            pstm.setTimestamp(3, new Timestamp(new Date().getTime())); // > Now
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                try {
                    PreparedStatement user = conn.prepareStatement("UPDATE user SET passwd = ? WHERE id_user = ?");
                    user.setString(1, info.getNew_passwd());
                    user.setInt(2, rs.getInt("id_user"));
                    user.executeUpdate();

                    res.put("success", true);
                    res.put("text", "Your password is changed :)");
                } catch (Exception e) {
                    res.put("success", false);
                    res.put("text", "Something Wrong :(");
                    e.printStackTrace();
                }
            } else {
                res.put("success", false);
                res.put("text", "Your verification code must be valid! :(");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Something Wrong :(");
        }
        return res;
    }

}
