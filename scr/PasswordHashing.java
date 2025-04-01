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


public class PasswordHashing {
    private String saltEncodeString = "[B@68de1454eg!.e";
    //To test
    // public static void main(String[] args) {
    //     hashingPassword("password");
    // }

    public String hashingPassword(String password){
        byte[] salt = new byte[16];
        try {

            salt = saltEncodeString.getBytes("UTF-8");

        } catch (UnsupportedEncodingException saltError) {
            System.out.println("Error occured when encoding salt");
            saltError.printStackTrace();
        }

        try{
            KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = f.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();
            // System.out.printf("salt: %s%n", enc.encodeToString(salt));
            // System.out.printf("hash: %s%n", enc.encodeToString(hash)); 
            return enc.encodeToString(hash);
        }catch(NoSuchAlgorithmException e){
            System.out.println("Error occured when encoding password");
            e.printStackTrace();
            return password;
        }catch(InvalidKeySpecException keyError){
            System.out.println("Error occured when encoding password");
            keyError.printStackTrace();
            return password;
        }
    }

    public boolean hashAllPassword(String filePath){

        String fileContent = "";
        String[] buffer;
        File file = new File(filePath);

        try{
            Scanner scanner = new Scanner(file);
            fileContent = fileContent + scanner.nextLine() + "\n";
            System.out.println(scanner.hasNextLine());
            while(scanner.hasNextLine()){
                buffer = scanner.nextLine().split(",");
                System.out.println(buffer[0]);

                buffer[4] = hashingPassword(buffer[4]);

                for(int i = 0; i < 5; i++){
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
        }catch(FileNotFoundException e){
            System.out.println("Error occured when reading " + filePath);
            e.printStackTrace();
            return false;
        }catch(IOException e){
            System.out.println("Error occured when writing " + filePath);
            e.printStackTrace();
            return false;
        }
    }
}
