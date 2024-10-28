package system.api.clinic.api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String toEmail) {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("SCHEDULE");
            helper.setText("<h3>Schedule done!</h3>", true);
            mailSender.send(message);
            log.info("EMAIL SENT");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
