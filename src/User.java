// import java.io.File;
// import java.io.FileNotFoundException;
// import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class User {
    private String name;
    private String NRIC;
    private String userID;
    private String password;
    private int age;
    private boolean maritalStatus;
    private MaritalStatus ms; //temporay placeholde for marital status

    //set methods
    public void setName(String name){
        this.name = name;
    }
    public void setNRIC(String NRIC){
        this.NRIC = NRIC;
    }
    public void setUserID(String UserID){
        this.userID = UserID;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setage(int age){
        this.age = age;
    }
    public void setMatritialSatus(boolean maritalStatus){
        this.maritalStatus = maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus ms) {
    	this.ms = ms; //temp placeholder for marital status
    }
    //get methods
    public String getName(){
        return this.name;
    }
    public String getNRIC(){
        return this.NRIC;
    }
    public String getUserID(){
        return this.userID;
    }
    public int getAge(){
        return this.age;
    }
    public boolean getMatritialSatus(){
        return this.maritalStatus;
    }
    public MaritalStatus getMaritalStatus() {
    	return ms;
    }
    
    //UserID is not included in excel file
    //Using name as login name instead
    public boolean login(String name, String password){
        PasswordHashing passwordHashing = new PasswordHashing();
        String hashedPassword = passwordHashing.hashingPassword(password);
        if((this.name.equals(name) || this.NRIC.equals(name)) && this.password.equals(hashedPassword)){
            return true;
        }else{
            return false;
        }
    }
    // public boolean logout();

    public boolean createEnquiry(){
        return false;
    }

    //This function may also used for change other user info
    //Will update this later on
    public boolean changePassword(String newPassword, String filePath){

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
                if(buffer[1].equals(this.NRIC)){
                    buffer[4] = newPassword;
                }

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

            this.password = newPassword;

            System.err.println("\n===Password change success===\n");

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

    public abstract Enquiry createEnquiry(Project project, String message);
    public abstract Enquiry[] viewEnquiries();
    public abstract void editEnquiry(Enquiry enquiry, String newMessage);
    public abstract void deletEnquiry(Enquiry enquiry);
    public abstract boolean canApply(Project project);
    // public abstract void viewEnquiry();
    // public abstract void editEnquiry();
    // public abstract void deletEnquiry();
    // public abstract boolean canApply();
}
