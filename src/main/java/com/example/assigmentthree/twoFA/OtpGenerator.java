package com.example.assigmentthree.twoFA;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpGenerator {

        public static String generateOTP(int length) {
            // Characters allowed in the OTP
            String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

            SecureRandom random = new SecureRandom();
            StringBuilder otp = new StringBuilder(length);

            for (int i = 0; i < length; i++) {
                int index = random.nextInt(characters.length());
                otp.append(characters.charAt(index));
            }

            return otp.toString();
        }
}
