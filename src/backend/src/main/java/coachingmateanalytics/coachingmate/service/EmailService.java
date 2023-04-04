package coachingmateanalytics.coachingmate.service;

//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
//import com.google.api.services.gmail.Gmail;
//import com.google.api.services.gmail.model.Draft;
//import com.google.api.services.gmail.model.Message;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

//
//@Service("emailService")
//@Slf4j
public class EmailService {
//
//
//    @Value("${reset.password.email.address}")
//    private String fromEmailAddress;
//    @Value("${reset.password.email.password}")
//    private String password;
//
//
//    @Async("emailPoolTaskExecutor")
//    public void SendEmail(String toEmailAddress) {
//        try{
//            String host = "smtp.gmail.com";
//
//            // Get system properties
//            Properties properties = System.getProperties();
//
//            // Setup mail server
//            properties.put("mail.smtp.host", host);
//            properties.put("mail.smtp.port", "465");
//            properties.put("mail.smtp.ssl.enable", "true");
//            properties.put("mail.smtp.auth", "true");
//
//            // Get the Session object.// and pass username and password
//            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//
//                protected PasswordAuthentication getPasswordAuthentication() {
//
//                    return new PasswordAuthentication(fromEmailAddress, password);
//
//                }
//
//            });
//
//            // Used to debug SMTP issues
//            session.setDebug(true);
//
//            Thread.sleep(3000);
//            log.info("User " + toEmailAddress + " send success");
//        } catch (InterruptedException e) {
//            System.out.println(e);
//        }
//    }
//
//
//    public static MimeMessage createEmail(String to,
//                                          String from,
//                                          String subject,
//                                          String bodyText)
//            throws MessagingException {
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//
//        MimeMessage email = new MimeMessage(session);
//
//        email.setFrom(new InternetAddress(from));
//        email.addRecipient(javax.mail.Message.RecipientType.TO,
//                new InternetAddress(to));
//        email.setSubject(subject);
//        email.setText(bodyText);
//        return email;
//    }
//
//    public static Message createMessageWithEmail(MimeMessage emailContent)
//            throws MessagingException, IOException {
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        emailContent.writeTo(buffer);
//        byte[] bytes = buffer.toByteArray();
//        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
//        Message message = createEmail(
//                "575628304@qq.com",
//                "menglingjun96@gmial.com",
//                "Boxjelly Reset Password",
//                "Hello Gmail");
////        message.setRaw(encodedEmail);
//        return message;
//    }
}
