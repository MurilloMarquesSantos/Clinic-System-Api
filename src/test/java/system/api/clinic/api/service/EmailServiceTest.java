package system.api.clinic.api.service;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.api.clinic.api.exception.EmailSendingException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    private static final String MESSAGE = "ERROR WHILE TRYING TO SEND EMAIL";

    private static final LocalDateTime DATE = LocalDateTime.of(2024, 11, 20, 14, 30);


    @BeforeEach
    void setUp() {

        BDDMockito.lenient().when(mailSender.createMimeMessage())
                .thenReturn(mimeMessage);

        BDDMockito.lenient().doNothing().when(mailSender).send(mimeMessage);
    }

    @Test
    void sendAppointmentConfirmation_SendEmail_WhenSuccessful() {

        assertThatCode(() -> emailService.sendAppointmentConfirmation(
                "example@gmail.com", "Joao", DATE))
                .doesNotThrowAnyException();
    }

    @Test
    void sendAppointmentConfirmation_ThrowsEmailSendingException_WhenAnErrorOccur() {

        assertThatExceptionOfType(EmailSendingException.class)
                .isThrownBy(() -> emailService.sendAppointmentConfirmation("", "", DATE))
                .withMessageContaining(MESSAGE);
    }


    @Test
    void sendDeleteAppointmentConfirmation_SendEmail_WhenSuccessful() {

        assertThatCode(() -> emailService.sendDeleteAppointmentConfirmation(
                "example@gmail.com", "Joao", DATE))
                .doesNotThrowAnyException();
    }

    @Test
    void sendDeleteAppointmentConfirmation_ThrowsEmailSendingException_WhenAnErrorOccur() {

        assertThatExceptionOfType(EmailSendingException.class)
                .isThrownBy(() -> emailService.sendDeleteAppointmentConfirmation("", "", DATE))
                .withMessageContaining(MESSAGE);
    }

    @Test
    void sendChangePasswordRequest_SendEmail_WhenSuccessful() {

        assertThatCode(() -> emailService.sendChangePasswordRequest("example@gmail.com", "Joao"))
                .doesNotThrowAnyException();
    }

    @Test
    void sendChangePasswordRequest_ThrowsEmailSendingException_WhenAnErrorOccur() {

        assertThatExceptionOfType(EmailSendingException.class)
                .isThrownBy(() -> emailService.sendChangePasswordRequest("", ""))
                .withMessageContaining(MESSAGE);
    }

    @Test
    void sendChangePasswordConfirmation_SendEmail_WhenSuccessful() {

        assertThatCode(() -> emailService.sendChangePasswordConfirmation("example@gmail.com"))
                .doesNotThrowAnyException();
    }

    @Test
    void sendChangePasswordConfirmation_ThrowsEmailSendingException_WhenAnErrorOccur() {

        assertThatExceptionOfType(EmailSendingException.class)
                .isThrownBy(() -> emailService.sendChangePasswordConfirmation(""))
                .withMessageContaining(MESSAGE);
    }

}