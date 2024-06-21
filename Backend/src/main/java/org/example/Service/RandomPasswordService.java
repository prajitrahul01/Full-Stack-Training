package org.example.Service;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomPasswordService {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*_+-=?";

    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;

    private static SecureRandom random = new SecureRandom();

    public static String generateRandomPassword(int length) {
        if (length < 12) throw new IllegalArgumentException("Password length must be at least 12 characters");

        StringBuilder sb = new StringBuilder(length);

        // Generate at least 3 characters of each type
        sb.append(generateRandomChars(CHAR_LOWER, 3));
        sb.append(generateRandomChars(CHAR_UPPER, 3));
        sb.append(generateRandomChars(NUMBER, 3));
        sb.append(generateRandomChars(OTHER_CHAR, 3));

        // Fill the remaining characters with random characters from the base string
        for (int i = 12; i < length; i++) {
            int randomIndex = random.nextInt(PASSWORD_ALLOW_BASE.length());
            sb.append(PASSWORD_ALLOW_BASE.charAt(randomIndex));
        }

        // Shuffle the password characters
        char[] passwordChars = sb.toString().toCharArray();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(length);
            char temp = passwordChars[i];
            passwordChars[i] = passwordChars[randomIndex];
            passwordChars[randomIndex] = temp;
        }

        return new String(passwordChars);
    }

    private static String generateRandomChars(String source, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(source.length());
            sb.append(source.charAt(randomIndex));
        }
        return sb.toString();
    }
}
