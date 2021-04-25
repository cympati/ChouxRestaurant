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
                System.out.println("ADMIN");
                // Select all reservation in a week
                PreparedStatement psmt = conn.prepareStatement("SELECT table_rsv.id_rsv, special_req, status_rsv, size, dt_start " +
                        "FROM table_rsv INNER JOIN reservation  WHERE table_rsv.id_rsv = reservation.id_rsv " +
                        "AND table_rsv.dt_start >= ? AND table_rsv.dt_start <= ?");
                // >= now - 20 min && <= now + 7
                Timestamp now = new Timestamp(new Date().getTime() + (20 * 60 * 1000)); // ms
                Timestamp next = new Timestamp(new Date().getTime() + 604800000);
                System.out.println(now + "\n" + next);
                psmt.setTimestamp(1, now);
                psmt.setTimestamp(2, next);
                ResultSet rs = psmt.executeQuery();
                ArrayList<Object> reserves = new ArrayList<>();
                boolean ok = false;

                // Loop each row of database
                while (rs.next()) {
                    System.out.println(rs.getString("dt_start"));
                    Map<String, Object> list = new HashMap<>();
                    list.put("id", rs.getInt("id_rsv"));
                    list.put("specialReq", rs.getString("special_req"));
                    list.put("status", rs.getString("status_rsv"));
                    list.put("size", rs.getInt("size"));
                    list.put("dateTime", rs.getString("dt_start"));
                    reserves.add(list);
                    ok = true;
                }

                if (ok) {
                    res.put("success", true);
                    res.put("isAdmin", true);
                    res.put("reserves", reserves);
                    return res;
                }

                res.put("success", false);
                res.put("isAdmin", true);
                res.put("text", "There is no reservation now :(");
                return res;
            }

            // USER
            // Select all reservation => id_use = id_use
            System.out.println("USER");
            PreparedStatement psmt_rsv = conn.prepareStatement("SELECT reservation.id_rsv, reservation.special_req, reservation.status_rsv, " +
                    "reservation.size, table_rsv.dt_start FROM reservation INNER JOIN table_rsv WHERE reservation.id_user = ? " +
                    "AND table_rsv.id_rsv = reservation.id_rsv");
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

                reserves.add(list);
                ok = true;
            }

            if (ok) {
                res.put("success", true);
                res.put("isAdmin", false);
                res.put("reserves", reserves);
                return res;
            }

            res.put("success", false);
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
