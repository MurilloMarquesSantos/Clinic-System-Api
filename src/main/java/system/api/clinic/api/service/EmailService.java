package system.api.clinic.api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import system.api.clinic.api.exception.EmailSendingException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendAppointmentConfirmation(String toEmail, String doctorName, LocalDateTime scheduleDate) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd - HH:mm");

        String date = scheduleDate.format(dateFormatter);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Appointment Confirmation");
            String htmlContent = String.format(
                    "<div style='font-family: Arial, sans-serif; color: #333;'>"
                            + "<h2>Appointment Confirmation</h2>"
                            + "<p>Hello,</p>"
                            + "<p>Your appointment with Dr. %s is confirmed!</p>"
                            + "<p><strong>Date and Time:</strong> %s</p>"
                            + "<br>"
                            + "<p>Best regards,</p>"
                            + "<p>The Clinic Team</p>"
                            + "</div>",
                    doctorName, date
            );
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendingException("ERROR TO SEND EMAIL");
        }
    }

    public void deleteAppointmentConfirmation(String toEmail, String doctorName, LocalDateTime scheduleDate) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd - HH:mm");

        String date = scheduleDate.format(dateFormatter);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Appointment Exclusion");
            String htmlContent = String.format(
                    "<div style='font-family: Arial, sans-serif; color: #333;'>"
                            + "<h2>Appointment Confirmation</h2>"
                            + "<p>Hello,</p>"
                            + "<p>Your appointment with Dr. %s is excluded!</p>"
                            + "<p><strong>Date and Time:</strong> %s</p>"
                            + "<br>"
                            + "<p>Best regards,</p>"
                            + "<p>The Clinic Team</p>"
                            + "</div>",
                    doctorName, date
            );
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendingException("ERROR TO SEND EMAIL");
        }
    }

}
