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
            rs.next();

            // เช็คว่าเป็นรหัสเดิมมั้ย
            if (!(info.getConfirmPasswd().equals(rs.getString("passwd")))) {
                PreparedStatement update = conn.prepareStatement("UPDATE user SET passwd = ? WHERE id_user = ?");
                update.setString(1, info.getConfirmPasswd());
                update.setInt(2, Integer.parseInt(id_user));
                update.executeUpdate();
                res.put("success", true);
                res.put("text", "Your password is changed :)");
                return res;
            } else if (info.getConfirmPasswd().equals(rs.getString("passwd"))){
                res.put("success", false);
                res.put("text", "Please, enter new password :(");
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
