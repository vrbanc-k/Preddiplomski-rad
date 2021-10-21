package hr.fer.zavrsni.service;

import hr.fer.zavrsni.util.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(final Mail mail) throws MessagingException {
        MimeMessage mimeMessage=emailSender.createMimeMessage();
        mimeMessage.setSubject(mail.getSubject());
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setText(mail.getContent(),true);
        emailSender.send(mimeMessage);

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setSubject(mail.getSubject());
//        message.setText(mail.getContent());
//        message.setTo(mail.getTo());
//        message.setFrom(mail.getFrom());
//
//        emailSender.send(message);
    }

}
