package com.example.assigmentthree.twoFA;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplate {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Your OTP for Verification");
        mimeMessageHelper.setText("""
        <div>
            <p><a href="https://localhost:8080/accountVerification?email=%s&otp=%s" target="_blank">Verify Account</a></p>
        </div>
        """.formatted(email, otp), true);

        javaMailSender.send(mimeMessage);
    }
}
