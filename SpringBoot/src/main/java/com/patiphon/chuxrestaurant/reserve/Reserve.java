package com.patiphon.chuxrestaurant.reserve;

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
    public Map<String, Object> _add(@CookieValue String token, @RequestParam long date_time, @RequestParam int size, @RequestParam String req) {
        Map<String, Object> res = new HashMap<>();
        try {
            String id_user = JwtUtil.parseToken(token);

            Connection conn = MySQLConnector.getConnection();
            PreparedStatement psmt_table = conn.prepareStatement("SELECT id_table FROM table_typ WHERE min <= ? AND max >= ?");
            psmt_table.setInt(1, size);
            psmt_table.setInt(2, size);
            ResultSet rs_table = psmt_table.executeQuery();

            while (rs_table.next()) {
                // check step 1
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = new Date(date_time);
                Date date2 = new Date(date_time - 7200000);


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
                    psmt_add_rsv.setString(1, req);
                    psmt_add_rsv.setInt(2, Integer.parseInt(id_user));
                    psmt_add_rsv.setInt(3, rs_table.getInt("id_table"));
                    psmt_add_rsv.setInt(4, size);
                    psmt_add_rsv.execute();

                    ResultSet generatedKeysRsv = psmt_add_rsv.getGeneratedKeys();
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
    public Map<String, Object> _cancel(@CookieValue String token, @RequestParam int id_rsv) {
        // ถ้าปุ่มคอมพลีทให้เปลี่ยนสเตตัสโนเป็นเลขหนึ่ง
        // ถ้าปุ่มแคนเซิลให้เปลี่ยนสเตตัสโนเป็นเลขสอง
        // รับสเตตัสโนมาเช็ค 1 , 2
        Map<String, Object> res = new HashMap<>();
        try {
            String id_user = JwtUtil.parseToken(token);

            Connection conn = MySQLConnector.getConnection();
            PreparedStatement psmt = conn.prepareStatement("UPDATE reservation SET status_rsv = 'cancel' " +
                    "WHERE id_user = ? AND id_rsv = ?");
            psmt.setInt(1, Integer.parseInt(id_user));
            psmt.setInt(2, id_rsv);
            int ok = psmt.executeUpdate();

            if (ok >= 1) {
                res.put("success", true);
                res.put("text", "Your reservation is cancelled :)");
                return res;
            }
            res.put("success", false);
            res.put("text1", "There are no this reservation ID obtained in your history :(");

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
            res.put("text1", "There are no this reservation ID obtained in your history :(");
            res.put("text2", "This reservation has been completed or cancelled. :(");
        } catch (SQLException e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text1", "Something Wrong :(");
        } catch (JwtException e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Token is incorrect :(");
        }
        return res;
    }

//    @PatchMapping(path = "/testEdit")
//    public Map<String, Object> _testEdit(@CookieValue String token, @RequestBody EditReserveDTO info) {
//        // Fail
//        Map<String, Object> res = new HashMap<>();
//        try {
//            String id_user = JwtUtil.parseToken(token);
//
//            Connection conn = MySQLConnector.getConnection();
//
//            //check step 1
//            PreparedStatement psmt_rsv1 = conn.prepareStatement("SELECT * FROM reservation WHERE id_user = ? AND id_rsv = ? AND status_rsv = 'pending'");
//            psmt_rsv1.setInt(1, Integer.parseInt(id_user));
//            psmt_rsv1.setInt(2, info.getId_rsv());
//            ResultSet rs_rsv1 = psmt_rsv1.executeQuery();
//
//            if (rs_rsv1.next()) {
//                // Select table that can contain this size
//                PreparedStatement psmt_table = conn.prepareStatement("SELECT id_table FROM table_typ WHERE min <= ? AND max >= ?");
//                psmt_table.setInt(1, info.getSize());
//                psmt_table.setInt(2, info.getSize());
//                ResultSet rs_table = psmt_table.executeQuery();
//
//                // เช็คว่า id_table จาก psmt_table ว่างไหม จาก date_time จาก tablr_rsv where id_rsv = ?
//                PreparedStatement psmt_dt1 = conn.prepareStatement("SELECT dt_start FROM table_rsv WHERE id_rsv = ?");
//                psmt_dt1.setInt(1, info.getId_rsv());
//                ResultSet rs_dt1 = psmt_dt1.executeQuery(); // dt_start of this reservation
//
//                while (rs_table.next()) {
//
//                    // check table is empty or not at that time
//                    rs_dt1.next();
//                    String date1 = rs_dt1.getString("dt_start");
//                    System.out.println("Date1 : " + date1);
//                    PreparedStatement psmt_dt2 = conn.prepareStatement("SELECT id_table FROM table_rsv WHERE dt_start <= ? AND dt_start >= ? AND id_table = ?");
//                    psmt_dt2.setString(1, date1);
//
//                    // change date2 to unix and then minus 7200000
//                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    long unixTime = 0;
//                    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0700")); //Specify my timezone
//                    try {
//                        unixTime = dateFormat.parse(rs_dt1.getString("dt_start")).getTime();
//                        System.out.println("UNIXTime : " + unixTime);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    Timestamp date2 = new Timestamp((unixTime) - 7200000);
//                    System.out.println("Date2 : " + dateFormat.format(date2));
//
//                    psmt_dt2.setString(2, dateFormat.format(date2)); // now - 2 hours
//                    psmt_dt2.setInt(3, rs_table.getInt("id_table"));
//                    ResultSet rs_dt2 = psmt_dt2.executeQuery();
//
//                    boolean ok = true;
//                    if (rs_dt2.next()) { // that table is not empty
//                        System.out.println(rs_dt2.getString("id_table") + " not empty");
//                        ok = false;
//                    }
//
//                    if (ok == true) {
//                        // check step 2 for sure
//                        PreparedStatement psmt_rsv2 = conn.prepareStatement("UPDATE reservation SET special_req = ?, id_table = ? WHERE id_user = ? AND id_rsv = ?");
//                        psmt_rsv2.setString(1, info.getSpecial_req());
//                        psmt_rsv2.setInt(2, rs_table.getInt("id_table"));
//                        psmt_rsv2.setInt(3, Integer.parseInt(id_user));
//                        psmt_rsv2.setInt(4, info.getId_rsv());
//                        psmt_rsv2.executeUpdate();
//
//                        PreparedStatement psmt = conn.prepareStatement("UPDATE table_rsv SET id_table = ? WHERE id_rsv = ?");
//                        System.out.println(rs_table.getInt("id_table"));
//                        psmt.setInt(1, rs_table.getInt("id_table"));
//                        System.out.println(rs_rsv1.getInt("id_rsv"));
//                        psmt.setInt(2, rs_rsv1.getInt("id_rsv"));
//                        psmt.executeUpdate();
//
//                        res.put("success", true);
//                        res.put("text", "Your reservation is changed :)");
//                        return res;
//                    }
//
//                }
//                res.put("success", false);
//                res.put("text", "There are no available table for your members :(");
//                return res;
//            }
//            res.put("success", false);
//            res.put("text", "Your reservation ID or status is incorrect :(");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            res.put("success", false);
//            res.put("text", "Something wrong :(");
//        } catch (JwtException e) {
//            e.printStackTrace();
//            res.put("success", false);
//            res.put("text", "Token is incorrect :(");
//        }
//        return res;
//    }
}
