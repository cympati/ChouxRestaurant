package com.patiphon.chuxrestaurant.reserves;

import com.patiphon.chuxrestaurant.database.MySQLConnector;
import com.patiphon.chuxrestaurant.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reserves")
public class Reserves {

    @GetMapping(path = "/all")
    public Map<String, Object> _admin(@CookieValue String token) {
        Map<String, Object> res = new HashMap<>();
        try {
            String id_user = JwtUtil.parseToken(token);
            Connection conn = MySQLConnector.getConnection();
            PreparedStatement psmt_isAdmin = conn.prepareStatement("SELECT isAdmin FROM user WHERE id_user = ?");
            psmt_isAdmin.setInt(1, Integer.parseInt(id_user));
            ResultSet rs_isAdmin = psmt_isAdmin.executeQuery();
            rs_isAdmin.next();

            // Check isAdmin
            if (rs_isAdmin.getBoolean("isAdmin")) { // This id_user is admin
                // Select all reservation in a week

                PreparedStatement psmt = conn.prepareStatement("SELECT table_rsv.id_rsv, table_rsv.dt_start, reservation.special_req, reservation.status_rsv, reservation.size, user.first_name, user.last_name, user.phone_no, user.email FROM table_rsv " +
                        "INNER JOIN reservation ON table_rsv.id_rsv = reservation.id_rsv " +
                        "INNER JOIN user ON reservation.id_user = user.id_user WHERE table_rsv.dt_start >= ? AND table_rsv.dt_start <= ?"); //

                // >= now - 20 min && <= now + 7
                Timestamp now = new Timestamp(new Date().getTime() + (20 * 60 * 1000)); // ms
                Timestamp next = new Timestamp(new Date().getTime() + 604800000);
                psmt.setTimestamp(1, now);
                psmt.setTimestamp(2, next);
                ResultSet rs = psmt.executeQuery();
                ArrayList<Object> reserves = new ArrayList<>();
                boolean ok = false;

                // Loop each row of database
                while (rs.next()) {
                    Map<String, Object> list = new HashMap<>();
                    list.put("id", rs.getInt("id_rsv"));
                    list.put("specialReq", rs.getString("special_req"));
                    list.put("status", rs.getString("status_rsv"));
                    list.put("size", rs.getInt("size"));
                    list.put("dateTime", rs.getString("dt_start"));
                    list.put("firstName", rs.getString("first_name"));
                    list.put("lastName", rs.getString("last_name"));
                    list.put("phone", rs.getString("phone_no"));
                    list.put("email", rs.getString("email"));

                    reserves.add(list);
                    ok = true;
                }

                if (ok) {
                    res.put("success", true);
                    res.put("isAdmin", true);
                    res.put("reserves", reserves);
                    return res;
                }

                res.put("success", true);
                res.put("isAdmin", true);
                res.put("text", "There is no reservation now :(");
                return res;
            }

            // USER
            // Select all reservation => id_use = id_use
            PreparedStatement psmt_rsv = conn.prepareStatement("SELECT table_rsv.id_rsv, table_rsv.dt_start, reservation.special_req, reservation.status_rsv, reservation.size, user.first_name, user.last_name, user.phone_no, user.email FROM table_rsv " +
                    "INNER JOIN reservation ON table_rsv.id_rsv = reservation.id_rsv " +
                    "INNER JOIN user ON reservation.id_user = user.id_user AND user.id_user = ?");

            psmt_rsv.setInt(1, Integer.parseInt(id_user));
            ResultSet rs_rsv = psmt_rsv.executeQuery();
            ArrayList<Object> reserves = new ArrayList<>();

            boolean ok = false;
            // Loop each row of database
            while (rs_rsv.next()) {
                Map<String, Object> list = new HashMap<>();
                list.put("id", rs_rsv.getInt("id_rsv"));
                list.put("specialReq", rs_rsv.getString("special_req"));
                list.put("status", rs_rsv.getString("status_rsv"));
                list.put("size", rs_rsv.getInt("size"));
                list.put("dateTime", rs_rsv.getString("dt_start"));

                // Information
                list.put("firstName", rs_rsv.getString("first_name"));
                list.put("lastName", rs_rsv.getString("last_name"));
                list.put("phone", rs_rsv.getString("phone_no"));
                list.put("email", rs_rsv.getString("email"));

                reserves.add(list);
                ok = true;
            }

            if (ok) {
                res.put("success", true);
                res.put("isAdmin", false);
                res.put("reserves", reserves);
                return res;
            }

            res.put("success", true);
            res.put("isAdmin", false);
            res.put("text", "There is no reservation now :(");
            return res;

        } catch (SQLException e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Something Wrong :(");
        } catch (JwtException e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Token is incorrect :(");
        }
        return res;
    }


}
