package com.patiphon.chuxrestaurant.reserve;

import com.patiphon.chuxrestaurant.DTO.AddReserveDTO;
import com.patiphon.chuxrestaurant.DTO.ApproveDTO;
import com.patiphon.chuxrestaurant.DTO.EditReserveDTO;
import com.patiphon.chuxrestaurant.database.MySQLConnector;
import com.patiphon.chuxrestaurant.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reserve")
public class Reserve {
    @PostMapping(path = "/add")
    public Map<String, Object> _add(@CookieValue String token, @RequestBody AddReserveDTO info) {
        Map<String, Object> res = new HashMap<>();
        try {

            long thirty_min = new java.util.Date().getTime() + 1800000; // now + 30
            System.out.println(thirty_min);

            if (info.getDate_time() <= thirty_min) {
                res.put("success", false);
                res.put("text", "Unavailable time :(");
                return res;
            }
            String id_user = JwtUtil.parseToken(token);
            System.out.println(id_user);
            System.out.println(info.toString());
            Connection conn = MySQLConnector.getConnection();
            PreparedStatement psmt_table = conn.prepareStatement("SELECT id_table FROM table_typ WHERE min <= ? AND max >= ?");
            psmt_table.setInt(1, info.getSize());
            psmt_table.setInt(2, info.getSize());
            ResultSet rs_table = psmt_table.executeQuery();

            while (rs_table.next()) {
                // check step 1
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = new Date(info.getDate_time());
                Date date2 = new Date(info.getDate_time() - 7200000);


                PreparedStatement psmt_rsv1 = conn.prepareStatement("SELECT id_table, id_rsv FROM table_rsv WHERE dt_start <= ? AND dt_start >= ? AND id_table = ?");
                psmt_rsv1.setString(1, dateFormat.format(date1));
                psmt_rsv1.setString(2, dateFormat.format(date2)); // now - 2 hours
                psmt_rsv1.setInt(3, rs_table.getInt("id_table"));
                ResultSet rs_rsv1 = psmt_rsv1.executeQuery();

                // check step 2
                boolean ok = true;
                while (rs_rsv1.next()) {
                    PreparedStatement psmt_rsv2 = conn.prepareStatement("SELECT id_table FROM reservation WHERE id_rsv = ? AND status_rsv = 'pending'");
                    psmt_rsv2.setInt(1, rs_rsv1.getInt("id_rsv"));
                    ResultSet rs_rsv2 = psmt_rsv2.executeQuery();

                    if (rs_rsv2.next()) { // Not empty
                        System.out.println(rs_rsv2.getString("id_table") + " not empty");
                        ok = false;
                    }
                }
                if (ok == true) {
                    PreparedStatement psmt_add_rsv = conn.prepareStatement("INSERT INTO reservation (special_req, id_user, id_table, size) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    psmt_add_rsv.setString(1, info.getReq());
                    psmt_add_rsv.setInt(2, Integer.parseInt(id_user));
                    psmt_add_rsv.setInt(3, rs_table.getInt("id_table"));
                    psmt_add_rsv.setInt(4, info.getSize());
                    psmt_add_rsv.execute();

                    try (ResultSet generatedKeysRsv = psmt_add_rsv.getGeneratedKeys()) {
                        if (generatedKeysRsv.next()) {
                            int key = generatedKeysRsv.getInt(1);
                            PreparedStatement psmt_add_table = conn.prepareStatement("INSERT INTO table_rsv (dt_start, id_table, id_rsv) VALUES (?, ?, ?)");
                            psmt_add_table.setString(1, dateFormat.format(date1));
                            psmt_add_table.setInt(2, rs_table.getInt("id_table"));
                            psmt_add_table.setInt(3, key);
                            psmt_add_table.execute();
                            res.put("success", true);
                            res.put("text", "Your reservation committed successfully :)");
                            return res;
                        } else {
                            res.put("success", false);
                            res.put("text", "Reservation failed, no reservation ID obtained :(");
                            return res;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        res.put("success", false);
                        res.put("text", "Something Wrong (generatedKeysRsv) :(");
                    }

                }
            }
            res.put("success", false);
            res.put("text", "There are no available table for that time :(");

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


    @PatchMapping(path = "/approve")
    public Map<String, Object> _cancel(@RequestBody ApproveDTO info) {
        // ถ้าปุ่มคอมพลีทให้เปลี่ยนสเตตัสโนเป็นเลขหนึ่ง
        // ถ้าปุ่มแคนเซิลให้เปลี่ยนสเตตัสโนเป็นเลขสอง
        // รับสเตตัสโนมาเช็ค 1 , 2
        System.out.println("Approve");
        Map<String, Object> res = new HashMap<>();
        try {
            Connection conn = MySQLConnector.getConnection();
            int ok = 0;

            if (info.getStatus() == 1) { // Complete
                PreparedStatement psmt_cp = conn.prepareStatement("UPDATE reservation SET status_rsv = 'complete' " +
                        "WHERE id_rsv = ?");
                psmt_cp.setInt(1, info.getId_rsv());
                ok = psmt_cp.executeUpdate();
                // Check it execute or not
                if (ok >= 1) {
                    res.put("success", true);
                    res.put("text", "Your reservation is completed :)");
                    return res;
                }
            }
            if (info.getStatus() == 2) { // Cancel
                PreparedStatement psmt_cc = conn.prepareStatement("UPDATE reservation SET status_rsv = 'cancel' " +
                        "WHERE id_rsv = ?");
                psmt_cc.setInt(1, info.getId_rsv());
                ok = psmt_cc.executeUpdate();
                // Check it execute or not
                if (ok >= 1) {
                    res.put("success", true);
                    res.put("text", "Your reservation is cancelled :)");
                    return res;
                }
            }
            res.put("success", false);
            res.put("text", "There are no this reservation ID obtained in your history :(");

        } catch (SQLException e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Something wrong :(");
        }
        return res;
    }

    @PatchMapping(path = "/edit")
    public Map<String, Object> _edit(@CookieValue String token, @RequestBody EditReserveDTO info) {
        Map<String, Object> res = new HashMap<>();
        try {
            String id_user = JwtUtil.parseToken(token);
            Connection conn = MySQLConnector.getConnection();
            PreparedStatement psmt_rsv2 = conn.prepareStatement("UPDATE reservation SET special_req = ? WHERE id_user = ? AND id_rsv = ? AND status_rsv = 'pending'");
            psmt_rsv2.setString(1, info.getSpecial_req());
            psmt_rsv2.setInt(2, Integer.parseInt(id_user));
            psmt_rsv2.setInt(3, info.getId_rsv());
            int ok = psmt_rsv2.executeUpdate();

            if (ok >= 1) {
                res.put("success", true);
                res.put("text", "Your reservation is changed :)");
                return res;
            }
            res.put("success", false);
            res.put("text", "There are no this reservation ID obtained in your history, or this reservation has been completed or cancelled. :(");
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
