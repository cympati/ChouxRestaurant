package com.patiphon.chuxrestaurant.user;

import com.patiphon.chuxrestaurant.database.MySQLConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.*;

@Configuration
@EnableScheduling
public class Email {

    @Autowired
    private JavaMailSender javaMailSender;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Bangkok") // Send email everyday at midnight
    public void doScheduledReminder() {

        try {
            Connection conn = MySQLConnector.getConnection();
            PreparedStatement pstm = conn.prepareStatement("SELECT table_rsv.id_rsv AS id_rsv, user.email AS email " +
                    "FROM ((table_rsv INNER JOIN reservation ON table_rsv.id_rsv = reservation.id_rsv) " +
                    "INNER JOIN user ON reservation.id_user = user.id_user AND user.getReminders = 1) WHERE dt_start >= ? AND dt_start <= ?");
            pstm.setTimestamp(1, new Timestamp(new java.util.Date().getTime() + (60 * 60 * 24 * 1000))); // now + 1day
            pstm.setTimestamp(2, new Timestamp(new java.util.Date().getTime() + ((60 * 60 * 24 * 1000) * 2))); // now + 2day
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setTo(rs.getString("email"));
//                msg.setTo("chancreamz@gmail.com");
                msg.setFrom("chuxreataurant@patiphon.cf");
                msg.setSubject("Chuxrestaurant password reset token");
                msg.setText("Reservation ID : " + rs.getInt("id_rsv"));
                javaMailSender.send(msg);
//                System.out.println("Reservation ID : " + rs.getInt("id_rsv")  + ", Email to " + rs.getString("email") + " has been sent");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
