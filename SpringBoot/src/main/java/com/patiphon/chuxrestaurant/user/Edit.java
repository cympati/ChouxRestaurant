package com.patiphon.chuxrestaurant.user;

import com.patiphon.chuxrestaurant.DTO.EditPasswdDTO;
import com.patiphon.chuxrestaurant.DTO.EditProfileDTO;
import com.patiphon.chuxrestaurant.database.MySQLConnector;
import com.patiphon.chuxrestaurant.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/edit")
public class Edit {
    @PatchMapping(path = "/profile")
    public Map<String, Object> _profile(@CookieValue String token, @RequestBody EditProfileDTO info) {
        Map<String, Object> res = new HashMap<>();
        try {
            String id_user = JwtUtil.parseToken(token);

            Connection conn = MySQLConnector.getConnection();
            PreparedStatement check = conn.prepareStatement("SELECT id_user FROM user WHERE id_user = ?");
            check.setInt(1, Integer.parseInt(id_user));
            ResultSet rs_check = check.executeQuery();

            if (rs_check.next()) {
                PreparedStatement psmt = conn.prepareStatement("UPDATE user\n" +
                        "SET first_name = ?, last_name = ?, phone_no = ?, email = ?, bg_color = ?, getReminders = ?\n" +
                        "WHERE id_user = ?");
                psmt.setString(1, info.getFirstname());
                psmt.setString(2, info.getLastname());
                psmt.setString(3, info.getPhone());
                psmt.setString(4, info.getEmail());
                psmt.setString(5, info.getColor());
                psmt.setBoolean(6, info.isGetReminders());
                psmt.setInt(7, Integer.parseInt(id_user));
                psmt.executeUpdate();
                res.put("success", true);
                res.put("text", "Your information is changed :)");
                return res;
            }
            res.put("success", false);
            res.put("text1", "There are no user ID obtained :(");
        } catch (SQLException e) {
            res.put("loginStatus", false);
            if (e instanceof SQLIntegrityConstraintViolationException) {
                if (e.getMessage().contains("user_phone_no_uindex")) {
                    res.put("text", "This phone number is already registered :(");
                } else if (e.getMessage().contains("user_email_uindex")) {
                    res.put("text", "This email is already registered :(");
                }
            } else {
                e.printStackTrace();
                res.put("text", "Something wrong :(");
            }
        } catch (JwtException e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Token is incorrect :(");
        }
        return res;
    }

    @PatchMapping(path = "/password")
    public Map<String, Object> _password(@CookieValue String token, @RequestBody EditPasswdDTO info) {
        Map<String, Object> res = new HashMap<>();
        try {
            String id_user = JwtUtil.parseToken(token);

            Connection conn = MySQLConnector.getConnection();
            PreparedStatement check = conn.prepareStatement("SELECT id_user FROM user WHERE id_user = ?");
            check.setInt(1, Integer.parseInt(id_user));
            ResultSet rs_check = check.executeQuery();

            if (rs_check.next()) {
                PreparedStatement psmt = conn.prepareStatement("UPDATE user SET passwd = ? WHERE id_user = ?");
                psmt.setString(1, info.getNewPassword());
                psmt.setInt(2, Integer.parseInt(id_user));
                psmt.executeUpdate();
                res.put("success", true);
                res.put("text", "Your password is changed :)");
                return res;
            }
            res.put("success", false);
            res.put("text1", "There are no user ID obtained :(");
        } catch (SQLException e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Something wrong :(");
        } catch (JwtException e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Token is incorrect :(");
        }
        return res;
    }
}
