package main.resources.util;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainText, String hashed) {
        return BCrypt.checkpw(plainText, hashed);
    }

    // Temporary main method to generate bcrypt hashes
    public static void main(String[] args) {
        System.out.println("Admin:    " + hashPassword("admin123"));
        System.out.println("HR:       " + hashPassword("hr123"));
        System.out.println("Employee: " + hashPassword("emp123"));
    }
}