package dai.smtp;

import org.junit.jupiter.api.Test;

public class EmailSenderTest {


    @Test
    void emailSenderTest(){
        String toEmail = "amirmouti4@gmail.com";
        String subject = "test";
        String body = "kndasnfksjnf";

        EmailSender.tlsEmailSend(toEmail, subject, body);
    }
}
