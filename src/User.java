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
    // private boolean maritalStatus;
    private MaritalStatus maritalStatus; //temporay placeholde for marital status
    // private MaritialStatus.maritialStatus maritialStatus;
    // private boolean maritalStatus;

    public User(){
        this("null","null","null","null",0,MaritalStatus.SINGLE);
    }
    public User(String name, String NRIC, String userID, String password, int age, MaritalStatus maritalStatus){
        this.name = name;
        this.NRIC = NRIC;
        this.userID = userID;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

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
    // public void setMatritialSatus(MaritialStatus.maritialStatus maritialStatus){
    //     this.maritialStatus = maritialStatus;
    // }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
    	this.maritalStatus = maritalStatus; //temp placeholder for marital status
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
    public MaritalStatus getMaritalStatus(){
        return this.maritalStatus;
    }
    
    //UserID is not included in excel file
    //Using name as login name instead
    public boolean login(String password){
        PasswordHashing passwordHashing = new PasswordHashing();
        String hashedPassword = passwordHashing.hashingPassword(password);
        if(this.password.equals(hashedPassword)){
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
    public boolean changeContent(String newContent, String filePath,String target){

        //To store the contents in the text file
        String fileContent = "";
        //to store info of the person
        String[] buffer;

        //
        File file = new File(filePath);
        PasswordHashing passwordHashing = new PasswordHashing();

        try{
            //use the scanner to scan through the file
            Scanner scanner = new Scanner(file);

            fileContent = fileContent + scanner.nextLine() + "\n";
            // System.out.println(scanner.hasNextLine());


            while(scanner.hasNextLine()){

                buffer = scanner.nextLine().split(",");
                // System.out.println(buffer[1]);
                // System.out.println(buffer[0]);
                if(buffer[1].equals(this.NRIC)){
                    switch (target.replace(" ","").toLowerCase()) {
                        case "name":
                            buffer[0] = newContent;
                            this.name = newContent;
                            break;
                        case "NRIC":
                            buffer[1] = newContent;
                            this.NRIC = newContent;
                            break;
                        case "age":
                            buffer[2] = newContent;
                            this.age = Integer.parseInt(newContent);
                            break;
                        //userID never used
                        // case "userID":
                        //     if(buffer[1].equals(this.NRIC)){
                        //         buffer[2] = passwordHashing.hashingPassword(newContent);
                        //     }
                        //     break;
                        case "marritialStatus":
                            buffer[3] = newContent;
                            if(newContent.equals("single")){
                                this.maritalStatus = MaritalStatus.SINGLE;
                            }else if(newContent.equals("married")){
                                this.maritalStatus = MaritalStatus.MARRIED;
                            }

                            break;
                        case "password":
                            buffer[4] = passwordHashing.hashingPassword(newContent);
                            this.password = buffer[4];
                            // Init init = new Init();
                            // init.LoadUserInfo();
                            break;
                        default:
                            break;
                    }
                }

                for(int i = 0; i < 5; i++){
                    // System.out.println(fileContent);
                    fileContent = fileContent + buffer[i] + ",";
                }
                fileContent = fileContent + "\n";   
            }
            FileWriter writer = new FileWriter(filePath);
            writer.write(fileContent);
            scanner.close();
            writer.close();

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
