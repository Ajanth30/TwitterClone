package com.Myapps.Twitter.ServiceImpl;

import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.Service.MailService;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

@AllArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    private Gmail gmail;


    @Override
    public ApiResponse sendMail(String to,long body) {
        try{
            MimeMessage email=createEmail(to,body);

            Message message=createMessageWithEmail(email);
            gmail.users().messages().send("ajanthanviki@gmail.com",message).execute();
            return ApiResponse.success(true);
        }
        catch (Exception e){
            e.printStackTrace();
            return ApiResponse.failure(false);
        }

    }


    private MimeMessage createEmail(String toEmailAddress,
                                    long bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress("twitterClone"));
        email.addRecipient(jakarta.mail.Message.RecipientType.TO,
                new InternetAddress(toEmailAddress));
        email.setSubject("Account Verification");
        email.setText(bodyText+"");
        return email;
    }

    private Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }


}
