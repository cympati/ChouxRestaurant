package com.patiphon.chuxrestaurant.account;

import com.patiphon.chuxrestaurant.DTO.LoginDTO;
import com.patiphon.chuxrestaurant.database.MySQLConnector;
import com.patiphon.chuxrestaurant.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// import libraries
@RestController
@RequestMapping("/auth")
public class Login {
    @GetMapping(path = "/user")
    public Map<String, Object> _user() {
        Map<String, Object> res = new HashMap<>();

        try {
            Connection connection = MySQLConnector.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM user");
            res.put("success", true);
            ArrayList<Object> arrList = new ArrayList<>();

//          Loop each row of database
            while (resultSet.next()) {
                Map<String, Object> list = new HashMap<>();
                list.put("id" , resultSet.getInt("id_user"));
                list.put("firstname" , resultSet.getString("first_name"));
                list.put("lastname" , resultSet.getString("last_name"));
                list.put("phoneNumber" , resultSet.getString("phone_no"));
                list.put("email" , resultSet.getString("email"));
                list.put("password" , resultSet.getString("passwd"));
                list.put("isAdmin" , resultSet.getBoolean("isAdmin"));
                arrList.add(list);
            }
            res.put("userList", arrList);
        } catch (SQLException e) {
            res.put("success", false);
        }
        return res;
    }

    @PostMapping(path = "/login")
    public Map<String, Object> _login(@RequestBody LoginDTO user) {
        Map<String, Object> res = new HashMap<>();
        try {
            Connection connection = MySQLConnector.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND passwd = ?");
            pstm.setString(1, user.getEmail());
            pstm.setString(2, user.getPassword());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String token = JwtUtil.generateToken(rs.getInt("id_user") + "");
                res.put("token", token);

                Map<String, Object> userDetail = new HashMap<>();
                Map<String, Object> bgColor = new HashMap<>();

                userDetail.put("id" , rs.getInt("id_user"));
                userDetail.put("firstname" , rs.getString("first_name"));
                userDetail.put("lastname" , rs.getString("last_name"));
                userDetail.put("phoneNumber" , rs.getString("phone_no"));
                userDetail.put("email" , rs.getString("email"));
                userDetail.put("isAdmin" , rs.getBoolean("isAdmin"));

                bgColor.put("color" , rs.getString("bg_color"));
                bgColor.put("value" , rs.getString("bg_color").toLowerCase());

                res.put("getRmd", rs.getBoolean("getReminders"));
                res.put("bgColor", bgColor);
                res.put("isLogin", true);
                res.put("userDetail", userDetail);
                res.put("text", "login successfully :)");
            } else {
                res.put("isLogin", false);
                res.put("text", "login fail :(");
            }
        } catch(SQLException e) {
            res.put("success", false);
            res.put("text", "Something Wrong :(");
            e.printStackTrace();
        }
        return res;
    }
}
