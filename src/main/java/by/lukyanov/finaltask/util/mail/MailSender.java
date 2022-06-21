package by.lukyanov.finaltask.util.mail;

import by.lukyanov.finaltask.util.ResourceBundleExtractor;
import java.util.concurrent.Executors;

import static by.lukyanov.finaltask.command.Message.*;

public class MailSender {
    private static final MailSender instance = new MailSender();

    public static MailSender getInstance(){
        return instance;
    }

    private MailSender() {
    }

    public boolean send(MailType type, String email, String currentLocale){
        ResourceBundleExtractor resource = new ResourceBundleExtractor(currentLocale);
        switch (type){
            case SIGN_UP -> createAndSend(email, resource.getValue(MAIL_SIGN_UP_SUBJECT), resource.getValue(MAIL_SIGN_UP_TEXT));
            case CONFIRM_ACCOUNT -> createAndSend(email, resource.getValue(MAIL_ACCEPT_ACC_SUBJECT), resource.getValue(MAIL_ACCEPT_ACC_TEXT));
            case DECLINE_ACCOUNT -> createAndSend(email, resource.getValue(MAIL_DECLINE_ACC_SUBJECT), resource.getValue(MAIL_DECLINE_ACC_TEXT));
            default -> {
                return false;
            }
        }
        return true;
    }

    private void createAndSend(String email, String subject, String text){
        CustomMail mail = new CustomMail(email.strip(), subject, text);
        var executor = Executors.newSingleThreadExecutor();
        executor.execute(mail);
    }
}
