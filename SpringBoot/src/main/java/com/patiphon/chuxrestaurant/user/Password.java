package com.patiphon.chuxrestaurant.user;

import com.patiphon.chuxrestaurant.DTO.ApproveDTO;
import com.patiphon.chuxrestaurant.DTO.ConfirmPasswordDTO;
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
@RequestMapping("/confirm")
public class Password {

    @PatchMapping(path = "/password")
    public Map<String, Object> _password(@CookieValue String token, @RequestBody ConfirmPasswordDTO info) {
        Map<String, Object> res = new HashMap<>();
        try {
            String id_user = JwtUtil.parseToken(token);
            Connection conn = MySQLConnector.getConnection();
            PreparedStatement pstm = conn.prepareStatement("SELECT passwd FROM user WHERE id_user = ?");
            pstm.setInt(1, Integer.parseInt(id_user));
            ResultSet rs = pstm.executeQuery();
            System.out.println(info.getConfirmPasswd());
            rs.next();
            if (info.getConfirmPasswd().equals(rs.getString("passwd"))) {
                System.out.println(info.getConfirmPasswd());
                res.put("success", true);
                res.put("text", "Your confirm password is correct :)");
                return res;
            }
            System.out.println(info.getConfirmPasswd());
            res.put("success", false);
            res.put("text", "Your confirm password must match :(");
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
