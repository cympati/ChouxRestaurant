package com.patiphon.chuxrestaurant.user;

import com.patiphon.chuxrestaurant.database.MySQLConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.*;
import java.text.SimpleDateFormat;

@Configuration
@EnableScheduling
public class Email {

    @Autowired
    private JavaMailSender javaMailSender;

    // 0 0 0 * * *
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Bangkok") // Send email everyday at midnight
    public void doScheduledReminder() {

        try {
            Connection conn = MySQLConnector.getConnection();
            PreparedStatement pstm = conn.prepareStatement("SELECT table_rsv.id_rsv AS id_rsv, table_rsv.dt_start AS date, user.email AS email " +
                    "FROM ((table_rsv INNER JOIN reservation ON table_rsv.id_rsv = reservation.id_rsv) " +
                    "INNER JOIN user ON reservation.id_user = user.id_user AND reservation.status_rsv = 'pending' AND user.getReminders = 1) WHERE dt_start >= ? AND dt_start <= ?");
            pstm.setTimestamp(1, new Timestamp(new java.util.Date().getTime() + (60 * 60 * 24 * 1000))); // now + 1day
            pstm.setTimestamp(2, new Timestamp(new java.util.Date().getTime() + ((60 * 60 * 24 * 1000) * 2))); // now + 2day
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SimpleMailMessage msg = new SimpleMailMessage();
                // Format
                String date_pattern = "EEEEE, dd MMMMM yyyy";
                String time_pattern = "HH:mm";
                SimpleDateFormat date_format = new SimpleDateFormat(date_pattern);
                SimpleDateFormat time_format = new SimpleDateFormat(time_pattern);
                String date = date_format.format(rs.getTimestamp("date"));
                String time = time_format.format(rs.getTimestamp("date"));

                msg.setTo(rs.getString("email"));
//                msg.setTo("chancreamz@gmail.com");
                msg.setFrom("chuxreataurant@patiphon.cf");
                msg.setSubject("Chuxrestaurant");
                msg.setText("Your reservation/booking with Chuxrestaurant is scheduled on " + date + " at " + time + ". \nYour booking no. is " + rs.getInt("id_rsv"));
                javaMailSender.send(msg);

//                System.out.println("Your reservation/booking with Chuxrestaurant is scheduled on " + date + " at " + time + ". \nYour booking no. is " + rs.getInt("id_rsv"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
