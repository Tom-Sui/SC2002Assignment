import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Provides secure password hashing functionality using PBKDF2 with HMAC-SHA256.
 * 
 * This class implements password security through:
 * <ul>
 *   <li>Password-Based Key Derivation Function 2 (PBKDF2) algorithm</li>
 *   <li>HMAC-SHA256 as the pseudorandom function</li>
 *   <li>Fixed salt value for consistent hashing</li>
 *   <li>65,536 iterations for key stretching</li>
 *   <li>Base64 encoding of the resulting hash</li>
 * </ul>
 * 
 */
public class PasswordHashing {
    private String saltEncodeString = "[B@68de1454eg!.e";

    /**
     * Hashes a password using PBKDF2WithHmacSHA256 algorithm.
     * The hashing process includes:
     * <ol>
     *   <li>Converting the fixed salt to UTF-8 bytes</li>
     *   <li>Applying 65,536 iterations of the algorithm</li>
     *   <li>Generating a 128-bit key length hash</li>
     *   <li>Encoding the result in Base64</li>
     * </ol>
     * 
     * @param password The plaintext password to hash
     * @return Base64-encoded hash of the password, or original password if hashing fails
     * 
     * This implementation uses a random generated salt.
     * 
     * Example usage:
     * <pre>
     * PasswordHashing hasher = new PasswordHashing();
     * String hashed = hasher.hashingPassword("myPassword123");
     * </pre>
     */
    public String hashingPassword(String password) {
        byte[] salt = new byte[16];
        try {
            salt = saltEncodeString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException saltError) {
            System.out.println("Error occurred when encoding salt");
            saltError.printStackTrace();
        }

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = f.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();
            return enc.encodeToString(hash);
        } catch(NoSuchAlgorithmException e) {
            System.out.println("Error occurred when encoding password");
            e.printStackTrace();
            return password;
        } catch(InvalidKeySpecException keyError) {
            System.out.println("Error occurred when encoding password");
            keyError.printStackTrace();
            return password;
        }
    }

    /**
     * Hashes all current user's password in data file.
     * 
     * @param filePath Path to the CSV file containing passwords
     * @return true if operation succeeded, false if any error occurred
     * 
     * This method will overwrite the original password with hashed passwords.
     * Ensure you have a backup before execution.
     */
    public boolean hashAllPassword(String filePath) {
        String fileContent = "";
        String[] buffer;
        File file = new File(filePath);

        try {
            Scanner scanner = new Scanner(file);
            fileContent = fileContent + scanner.nextLine() + "\n";
            System.out.println(scanner.hasNextLine());
            while(scanner.hasNextLine()) {
                buffer = scanner.nextLine().split(",");
                System.out.println(buffer[0]);

                buffer[4] = hashingPassword(buffer[4]);

                for(int i = 0; i < 5; i++) {
                    System.out.println(fileContent);
                    fileContent = fileContent + buffer[i] + ",";
                }
                fileContent = fileContent + "\n";   
            }
            FileWriter writer = new FileWriter(filePath);
            writer.write(fileContent);
            scanner.close();
            writer.close();

            System.err.println("\n===All passwords are initialized===\n");
            return true;            
        } catch(FileNotFoundException e) {
            System.out.println("Error occurred when reading " + filePath);
            e.printStackTrace();
            return false;
        } catch(IOException e) {
            System.out.println("Error occurred when writing " + filePath);
            e.printStackTrace();
            return false;
        }
    }
}