import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class App{
    //To keep track who is loged in
    private static String filePath = "./Data";  // TO ADD /src/ FOR ECLIPSE
    private static ArrayList<HDBOfficer> hdbOfficers = new ArrayList<>();
    private static ArrayList<HDBManager> hdbManagers = new ArrayList<>();
    private static ArrayList<Applicant> applicant = new ArrayList<>();
    private static ArrayList<Project> projectList = new ArrayList<>();
    private static boolean logedIn = false;
    private static String userType = "NULL";
    private static String currentUserId = "NULL";
    private static int userPos = -1;

    public static void main(String[] args) {
        String userInput;
        Scanner scanner = new Scanner(System.in);

        //Only run when there is new user are manually added in
        // PasswordHashing passwordHashing = new PasswordHashing();
        // passwordHashing.hashAllPassword("./Data/ApplicantList.txt");
        // passwordHashing.hashAllPassword("./Data/ManagerList.txt");
        // passwordHashing.hashAllPassword("./Data/OfficerList.txt");
        
        //Initialize all user info into objects
        Init init = new Init();

        //Initialize Applicant info into Applicant class
        applicant = init.LoadUserInfo();

        // System.out.println(applicant.get(0).login(userPassword));
        // System.exit(0);
        //Initialize HDB officier info into HDBOfficier class
        hdbManagers = init.LoadManagerInfo();

        //Initialize HDB manager info into HDBManager class
        hdbOfficers = init.LoadOfficerInfo();

        //Initialize projects info into Project class
        projectList = init.LoadProjectInfo(hdbManagers, hdbOfficers);
        // hdbInterface.interface(project,hdbManagers,hdbOfficers)
        // Return null if there is maching manager found
        // projects = hdbManagers.get(1).createProject("aNOTHER NAME,TESTING TESTING name,2-Room,2,350000,3-Room,3,450000,2/2/2343,12/12/2343,Jessica,3,Daniel&Emily,true",projects,hdbManagers,hdbOfficers);
        // hdbManagers.get(1).editProject("Acacia Breeze",
        //                                      "12/12/1212", 
        //                                      "5",
        //                                      projects,
        //                                      0, 
        //                                      hdbManagers,
        //                                      hdbOfficers);
        // projects = init.LoadProjectInfo(hdbManagers, hdbOfficers);
        // System.exit(0);
        //Initialize manager managed projects
        hdbManagers = init.setManagerManagedProjects(hdbManagers,projectList);
        
        init.setOfficerManagedProjects(hdbOfficers, projectList);
        
        //Initialize Application info
        ArrayList<Application> applications = init.loadApplicationInfo(applicant, projectList);
        
       init.loadBookingInfo(applications, hdbOfficers);
        
        // General general = new General();
        // General.editProjectFile(projects.get(1),"New Name");
        // System.exit(0);
        //projects.get(1).setProjectName("New Name");
        // System.exit(0);
        
        // System.out.println(hdbManagers.get(0).getName());
        // hdbManagers.get(0).deletProject(projects.get(0));

        // System.exit(0);
        //Example of how to edit project


        //return helpinfo (cmds)
        helpInfo();
        System.out.print("Enter cmd: ");
        userInput = scanner.nextLine();

        //Porbably more ideal login function are module
        //Will update later on

        while(!userInput.equals("quit") && !userInput.equals("9")){
            switch (userInput) {
                case "1":
                    helpInfo();
                    break;
                case "2":
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
                    ArrayList<String> userList = new ArrayList<>(Arrays.asList("1", "2", "3"));
                    
                    // to update status for all projects
                    HDBManager mainManager = ManagerFactory.defaultManager(); 
                    mainManager.updateMaritalStatus(projectList);
                    
                    while(true){
                        if(userList.contains(userType)){
                            break;
                        }else{
                            System.out.print("User type: ");
                            userType = scanner.nextLine().toLowerCase();
                        }
                    }

                    System.out.print("Enter username: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String userPassword = scanner.nextLine();

                    

                    //There should be a more efficient way to achieve login function
                    //Will update this method later on

                    switch (userType.toLowerCase()){
                        case "hdb manager","1":
                            if(handleManagerLogin(userName, userPassword, hdbManagers, projectList, hdbOfficers, applicant)){
                                break;
                            }
                            loginFailed();
                            break;
                        case "hdb officer","2":
                            if(handleOfficerLogin(userName, userPassword, hdbOfficers)){
                                break;
                            }
                            loginFailed();
                            break;
                        case "applicant","3":
                            if(handleApplicantLogin(userName, userPassword, applicant)){
                                break;
                            }
                            loginFailed();
                            break;
                        default:
                            break;
                    }
                    break;
                case "edit profile","3":
                        if(!logedIn){
                            System.out.println("You have to login first");
                            break;
                        }
                        System.out.println("======Instructions======");
                        System.out.println("1. name");
                        System.out.println("2. NRIC");
                        System.out.println("3. age");
                        System.out.println("4. marritialStatus(singale/married)");
                        System.out.println("5. password");
                        System.out.println("========================");
                        int changeTarget = scanner.nextInt();
                        switch (userType){
                            case "hdb manager","1":
                                hdbManagers.get(userPos).editProfile(filePath + "/ManagerList.txt",changeTarget);
                                break;
                            case "hdb officer","2":
                                hdbOfficers.get(userPos).editProfile(filePath + "/OfficerList.txt",changeTarget);
                                break;
                            case "applicant","3":
                                applicant.get(userPos).editProfile(filePath + "/ApplicantList.txt",changeTarget);
                                break;
                            default:
                                System.out.println("Error occured == could not find user type");
                                break;
                        }
                        userType = "NULL";
                        userName = "NULL";
                        userPos = -1;
                        logedIn = false;
                    break;
                case "4":
                    logedIn = false;
                    userType = "NULL";
                    userName = "NULL";
                    userPos = -1;
                    break;
                case "5":
                    if(!logedIn){
                        System.out.println("You have to login first");
                        break;
                    }
                    System.out.println("Opening Enquiry Application...");
                    User currentUser = null;
                    
                    // Determine the current user type and get the appropriate user object
                    switch (userType.toLowerCase()) {
                        case "hdb manager":
                            currentUser = hdbManagers.get(userPos);
                            break;
                        case "hdb officer":
                            currentUser = hdbOfficers.get(userPos);
                            break;
                        case "applicant":
                            currentUser = applicant.get(userPos);
                            break;
                        default:
                            System.out.println("Error: Could not determine user type");
                            break;
                    }
                    
                    if (currentUser != null) {
                        EnquiryApp.start(currentUser);
                    }
                    break;
                default:
                    System.out.println("\n!!!Wrong input!!!\n");
                    break;
            }
            System.out.print("Enter cmd: ");
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
        System.out.println("3. edit profile");
        System.out.println("4. logout");
        System.out.println("5. enquiries");
        System.out.println("9. quit");
        System.out.println("========================");
    }

    public static void loginSuccess(User user){
        System.out.println("===Login success===");
        System.out.println("Welcome " + user.getName());
        System.out.println("===================\n");
    }

    public static void loginFailed(){
        System.out.println("============Login failed============");
        System.out.println("Incorrect password or incorrect ID");
        System.out.println("====================================");
    }
    
    /**
     * Shows the post-login menu with options for the user
     * 
     * @param user the logged-in user
     * @return true if the user wants to continue with their regular tasks, false if they want to exit
     */
    public static boolean showPostLoginMenu(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n======Post-Login Menu======");
        System.out.println("1. Continue with regular tasks");
        System.out.println("2. Open Enquiry Application");
        System.out.println("3. Logout");
        System.out.println("===========================");
        System.out.print("Enter your choice: ");
        
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1":
                return true;
            case "2":
                System.out.println("Opening Enquiry Application...");
                EnquiryApp.start(user);
                return false;
            case "3":
                logedIn = false;
                userType = "NULL";
                currentUserId = "NULL";
                userPos = -1;
                return false;
            default:
                System.out.println("Invalid choice. Continuing with regular tasks.");
                return true;
        }
    }
    
    /**
     * Handles login for HDB Manager
     * 
     * @param userName the username or NRIC
     * @param userPassword the password
     * @param hdbManagers list of HDB managers
     * @param projects list of projects
     * @param hdbOfficers list of HDB officers
     * @param applicant list of applicants
     * @return true if login successful, false otherwise
     */
    public static boolean handleManagerLogin(String userName, String userPassword, 
                                           ArrayList<HDBManager> hdbManagers,
                                           ArrayList<Project> projects,
                                           ArrayList<HDBOfficer> hdbOfficers,
                                           ArrayList<Applicant> applicant) {
        for(int i = 0; i < hdbManagers.size(); i++){
            if(hdbManagers.get(i).getName().equals(userName) || hdbManagers.get(i).getNRIC().equals(userName)){
                if(hdbManagers.get(i).login(userPassword)){
                    loginSuccess(hdbManagers.get(i));
                    userPos = i;
                    currentUserId = userName;
                    logedIn = true;
                    
                    // Show post-login menu
                    if (showPostLoginMenu(hdbOfficers.get(i))) {
                        ApplicantOfficerApp.start(hdbOfficers.get(i), projectList);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Handles login for Applicant
     * 
     * @param userName the username or NRIC
     * @param userPassword the password
     * @param applicant list of applicants
     * @return true if login successful, false otherwise
     */
    public static boolean handleApplicantLogin(String userName, String userPassword, 
                                             ArrayList<Applicant> applicant) {
        for(int i = 0; i < applicant.size(); i++){
            if(applicant.get(i).getName().equals(userName) || applicant.get(i).getNRIC().equals(userName)){
                if(applicant.get(i).login(userPassword)){
                    loginSuccess(applicant.get(i));
                    userPos = i;
                    currentUserId = userName;
                    logedIn = true;
                    
                    // Show post-login menu
                    if (showPostLoginMenu(applicant.get(i))) {
                        // Launch applicant interface
                        ApplicantOfficerApp.start(applicant.get(i), projectList);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean handleOfficerLogin(String userName, String userPassword, ArrayList<HDBOfficer> officers) {
        for (int i = 0; i < officers.size(); i++) {
            if (officers.get(i).getNRIC().equals(userName) && officers.get(i).login(userPassword)) {
                userPos = i;
                userType = "HDB Officer";
                currentUserId = userName;
                logedIn = true;
                // Show post-login menu
                if (showPostLoginMenu(hdbOfficers.get(i))) {
                    ApplicantOfficerApp.start(hdbOfficers.get(i), projectList);
                }
                return true;
            }
        }
        return false;
    }
}