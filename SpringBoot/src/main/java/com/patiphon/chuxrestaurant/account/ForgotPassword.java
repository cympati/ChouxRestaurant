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
    public Map<String, Object> _forgot(@CookieValue String token, @RequestBody LoginDTO info) {
        Map<String, Object> res = new HashMap<>();
        try {
            String id_user = JwtUtil.parseToken(token);

            Connection conn = MySQLConnector.getConnection();
            PreparedStatement check = conn.prepareStatement("SELECT id_user FROM user WHERE id_user = ?");
            check.setInt(1, Integer.parseInt(id_user));
            ResultSet rs_check = check.executeQuery();

            if (rs_check.next()) {
                PreparedStatement pstm = conn.prepareStatement("INSERT INTO reset_passwd (verify_cd, expire_tm, id_user) " +
                        "SELECT ? as verify_cd, ? as expire_tm, id_user from user WHERE email = ? AND id_user = ?", Statement.RETURN_GENERATED_KEYS);

                // Random
                String generatedString = RandomStringUtils.randomAlphanumeric(6);
                pstm.setString(1, generatedString);

                Timestamp expire_tm = new Timestamp(new java.util.Date().getTime() + (15 * 60 * 1000)); // 15 minutes
                pstm.setTimestamp(2, expire_tm);
                pstm.setString(3, info.getEmail());
                pstm.setInt(4, rs_check.getInt("id_user"));
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
                            javaMailSender.send(msg);
                            res.put("success", true);
                            res.put("text", "Please check your email for verification code :)");
                            return res;
                        } else {
                            res.put("success", false);
                            res.put("text", "Creating user failed, no ID obtained :(");
                            return res;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        res.put("success", false);
                        res.put("text", "Something Wrong (generatedKeys) :(");
                    }
                } else {
                    res.put("success", false);
                    res.put("text", "Creating user failed, it is not email of this user ID :(");
                    return res;
                }
            }
            res.put("success", false);
            res.put("text1", "There are no user ID obtained :(");
            return res;
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
                   PreparedStatement  user = conn.prepareStatement("UPDATE user SET passwd = ? WHERE id_user = ?");
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
                res.put("text", "User ID or your verification code is incorrect :(");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Something Wrong :(");
        }
        return res;
    }

}
