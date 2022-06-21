package by.lukyanov.finaltask.util.mail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class CustomMail implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private static final String MAIL_CONFIG_FILE = "email.properties";
    private static final String SENDER_MAIL = "mail.user.name";
    private static final String SENDER_PASS = "mail.user.password";
    private String recipient;
    private String subject;
    private String messageText;
    private Session mailSession;

    public CustomMail(String recipient, String subject, String messageText) {
        this.recipient = recipient;
        this.subject = subject;
        this.messageText = messageText;
    }

    private boolean init(){
        Properties properties = new Properties();
        try (InputStream inputStream = CustomMail.class.getClassLoader()
                .getResourceAsStream(MAIL_CONFIG_FILE)) {
            properties.load(inputStream);
            mailSession = createSession(properties);
            return true;
        } catch (IOException e) {
            logger.error("Error reading properties for email", e);
            return false;
        }
    }

    public boolean sendEmail(){
        if (init()){
            try {
                Message message = createMessage(recipient, subject, messageText);
                Transport.send(message);
                logger.info("email sent");
                return true;
            } catch (MessagingException e) {
                logger.warn("Mail can not be sent", e);
            }
        } else {
            logger.warn("Mail can not be sent");
        }
        return false;
    }

    private Message createMessage(String recipient, String subject, String messageText) throws MessagingException {
        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(subject);
        message.setText(messageText);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSentDate(new Date());
        return message;
    }

    private Session createSession(Properties properties){
        String sender = properties.getProperty(SENDER_MAIL);
        String password = properties.getProperty(SENDER_PASS);
        return Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender, password);
                    }
                });
    }

    @Override
    public void run() {
        sendEmail();
    }
}
