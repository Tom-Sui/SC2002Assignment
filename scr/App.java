import java.util.Scanner;
import java.util.ArrayList;
import java.sql.Date;

public class App {
    //To keep track who is loged in

    public static void main(String[] args) {
        String userInput;
        boolean logedIn = false;
        String currentUserId = "NULL";
        String userType = "NULL";
        int userPos = -1;

        Scanner scanner = new Scanner(System.in);

        //Only run when there is new user are manually added in
        // PasswordHashing passwordHashing = new PasswordHashing();
        // passwordHashing.hashAllPassword("./Data/ApplicantList.txt");
        // passwordHashing.hashAllPassword("./Data/ManagerList.txt");
        // passwordHashing.hashAllPassword("./Data/OfficerList.txt");
        
        //Initialize all user info into objects
        Init init = new Init();

        //Initialize Applicant info int Applicant class
        Applicant[] applicant;
        applicant = init.LoadUserInfo();

        //Initialize HDB officier info into HDBOfficier class
        HDBManager[] hdbManagers;
        hdbManagers = init.LoadManagerInfo();

        //Initialize HDB manager info into HDBManager class
        HDBOfficer[] hdbOfficers;
        hdbOfficers = init.LoadOfficerInfo();

        //return helpinfo (cmds)
        helpInfo();
        System.out.print("Enter instruction: ");
        userInput = scanner.nextLine();

        //Porbably more ideal login function are module
        //Will update later on

        while(!userInput.equals("quit")){
            switch (userInput) {
                case "help":
                    helpInfo();
                    break;
                case "login":
                    if(logedIn){
                        System.out.println("Already login as: " + currentUserId);
                        break;
                    }

                    System.out.println("===User type===");
                    System.out.println("1. HDB Manager");
                    System.out.println("2. HDB Officer");
                    System.out.println("3. Applicant");
                    System.out.println("===============");
                    userType = "NULL";
                    while(true){
                        if(userType.equals("hdb manager") || 
                        userType.equals("hdb officer") || 
                        userType.equals("applicant")){
                            break;
                        }else{
                            System.out.print("User type: ");
                            userType = scanner.nextLine().toLowerCase();
                        }
                    }

                    System.out.print("Enter username (case-sensitive): ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter password (case-sensitive): ");
                    String userPassword = scanner.nextLine();

                    //There should be a more efficient way to achieve login function
                    //Will update this method later on

                    switch (userType.toLowerCase()) {
                        case "hdb manager":
                        for(int i = 0; i < hdbManagers.length + 1; i++){
                            if(i == hdbManagers.length){
                                System.out.println("============Login failed============");
                                System.out.println("Incorrect password or incorrect ID");
                                System.out.println("====================================");
                                System.out.print("Enter username (case-sensitive): ");
                                userName = scanner.nextLine();
                                System.out.print("Enter password (case-sensitive): ");
                                userPassword = scanner.nextLine();
                                i = 0;
                            }
                            if(hdbManagers[i].login(userName, userPassword)){
                                System.out.println("===Login success===");
                                System.out.println("Welcome " + hdbManagers[i].getName());
                                System.out.println("===================\n");
                                userPos = i;
                                currentUserId = userName;
                                logedIn = true;
                                break;
                            }
                        }
                            break;

                        case "hdb officer":
                        for(int i = 0; i < hdbOfficers.length + 1; i++){
                            if(i == hdbOfficers.length){
                                System.out.println("============Login failed============");
                                System.out.println("Incorrect password or incorrect ID");
                                System.out.println("====================================");
                                System.out.print("Enter username (case-sensitive): ");
                                userName = scanner.nextLine();
                                System.out.print("Enter password (case-sensitive): ");
                                userPassword = scanner.nextLine();
                                i = 0;
                            }
                            if(hdbOfficers[i].login(userName, userPassword)){
                                System.out.println("===Login success===");
                                System.out.println("Welcome " + hdbOfficers[i].getName());
                                System.out.println("===================\n");
                                userPos = i;
                                currentUserId = userName;
                                logedIn = true;
                                break;
                            }
                        }
                            break;

                        case "applicant":
                            for(int i = 0; i < applicant.length + 1; i++){
                                if(i == applicant.length){
                                    System.out.println("============Login failed============");
                                    System.out.println("Incorrect password or incorrect ID");
                                    System.out.println("====================================");
                                    System.out.print("Enter username (case-sensitive): ");
                                    userName = scanner.nextLine();
                                    System.out.print("Enter password (case-sensitive): ");
                                    userPassword = scanner.nextLine();
                                    i = 0;
                                }
                                if(applicant[i].login(userName, userPassword)){
                                    System.out.println("===Login success===");
                                    System.out.println("Welcome " + applicant[i].getName());
                                    System.out.println("===================\n");
                                    
                                    // Dummy initialization of projects
                                    ArrayList<Project> projectList = new ArrayList<Project>();
                                    
                                    
                                    // Dummy Project 
                                    String projectName = "Sunrise Residences";
                                    String neighborhood = "Punggol";
                                    int twoRoomUnits = 50;
                                    int threeRoomUnits = 75;

                                    // Use java.sql.Date for consistency with your class
                                    Date openingDate = Date.valueOf("2025-05-01");
                                    Date closingDate = Date.valueOf("2025-05-15");

                                    // Assuming HDBManager and MaritialStatus have default constructors
                                    HDBManager dummyManager = new HDBManager();
                                    int officerSlots = 5;
                                    boolean isVisible = true;

                                    ArrayList<Applicant> applicants = new ArrayList<>();
                                    ArrayList<HDBOfficer> officers = new ArrayList<>();
                                    ArrayList<Enquiry> enquiries = new ArrayList<>();
                                    applicant[i].setMaritalStatus(MaritalStatus.SINGLE);
                                    
                                    MaritalStatus maritalStatus = MaritalStatus.MARRIED;

                                    Project dummyProject = new Project(
                                        projectName,
                                        neighborhood,
                                        twoRoomUnits,
                                        threeRoomUnits,
                                        openingDate,
                                        closingDate,
                                        dummyManager,
                                        officerSlots,
                                        isVisible,
                                        applicants,
                                        officers,
                                        enquiries,
                                        maritalStatus
                                    );
                                    
                                    projectList.add(dummyProject);
                                    // Launch applicant interface
                                    ApplicantApp applicantApp = new ApplicantApp();
                                    applicantApp.start(applicant[i], projectList);
                                    
                                    userPos = i;
                                    currentUserId = userName;
                                    logedIn = true;
                                    break;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case "change password":
                        if(!logedIn){
                            System.out.println("You have to login first");
                            break;
                        }
                        //for now will be calling direct from User class
                        System.out.println("Enter new password ");
                        String newPassword = scanner.nextLine();
                        System.out.println("Enter new password again: ");
                        while (!newPassword.equals(scanner.nextLine())) {
                            System.out.println("!!!Wrong password!!!");
                            System.out.println("Enter new password ");
                            newPassword = scanner.nextLine();
                            System.out.println("Enter new password again: ");
                        }

                        System.out.println(userType);
                        
                        switch (userType) {
                            case "hdb manager":
                                hdbManagers[userPos].changePassword(newPassword,"./scr/Data/ManagerList.txt");
                                break;
                            case "hdb officer":
                                hdbOfficers[userPos].changePassword(newPassword, "./scr/Data/OfficerList.txt");
                                break;
                            case "applicant":
                                System.out.println(userPos);
                                applicant[userPos].changePassword(newPassword, "./scr/Data/ApplicantList.txt");
                                break;
                            default:
                                System.out.println("Error occured == could not find user type");
                                break;
                        }
                    break;
                case "logout":
                    logedIn = false;
                    userType = "NULL";
                    userName = "NULL";
                    userPos = -1;
                    break;
                default:
                    System.out.println("\n!!!Wrong input!!!\n");
                    break;
            }
            System.out.print("Enter instruction: ");
            userInput = scanner.nextLine();
        }
        scanner.close();
    }

    public static void helpInfo(){
        //little instructions guids for user
        //the instruction we can change later on or remove it
        System.out.println("======Instructions======");
        System.out.println("1. help");
        System.out.println("2. login");
        System.out.println("3. change password");
        System.out.println("4. logout");
        System.out.println("9. quit");
        System.out.println("========================");
    }
}