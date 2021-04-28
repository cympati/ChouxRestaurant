package com.patiphon.chuxrestaurant.account;

import com.patiphon.chuxrestaurant.database.MySQLConnector;
import com.patiphon.chuxrestaurant.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoadData {
    @GetMapping(path = "/load")
    public Map<String, Object> _load(@CookieValue String token) {
        Map<String, Object> res = new HashMap<>();
        try {
            String id_user = JwtUtil.parseToken(token);

            Connection connection = MySQLConnector.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user WHERE id_user = ?");
            pstm.setString(1, id_user);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Map<String, Object> userDetail = new HashMap<>();
                Map<String, Object> bgColor = new HashMap<>();

                userDetail.put("id", rs.getInt("id_user"));
                userDetail.put("firstName", rs.getString("first_name"));
                userDetail.put("lastName", rs.getString("last_name"));
                userDetail.put("phoneNumber", rs.getString("phone_no"));
                userDetail.put("email", rs.getString("email"));
                userDetail.put("isAdmin", rs.getBoolean("isAdmin"));

                bgColor.put("color", rs.getString("bg_color"));
                bgColor.put("value", rs.getString("bg_color").toLowerCase());

                res.put("getRmd", rs.getBoolean("getReminders"));
                res.put("bgColor", bgColor);
                res.put("isLogin", true);
                res.put("userDetail", userDetail);
            } else {
                res.put("isLogin", false);
            }
        } catch (JwtException e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Token is incorrect :(");
        } catch (Exception e) {
            res.put("success", false);
            res.put("text", "Something Wrong :(");
            e.printStackTrace();
        }
        return res;
    }
}
