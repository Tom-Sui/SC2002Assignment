import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.security.GeneralSecurityException;
import java.sql.Date;

public class App{
    //To keep track who is loged in
    private static String filePath = "./Data";  // TO ADD /src/ FOR ECLIPSE
    private static boolean logedIn = false;
    private static String currentUserId = "NULL";
    private static String userType = "NULL";
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
        ArrayList<Applicant> applicant = init.LoadUserInfo();

        // System.out.println(applicant.get(0).login(userPassword));
        // System.exit(0);
        //Initialize HDB officier info into HDBOfficier class
        ArrayList<HDBManager> hdbManagers;
        hdbManagers = init.LoadManagerInfo();

        //Initialize HDB manager info into HDBManager class
        ArrayList<HDBOfficer> hdbOfficers;
        hdbOfficers = init.LoadOfficerInfo();

        //Initialize projects info into Project class
        ArrayList<Project> projects;
        projects = init.LoadProjectInfo(hdbManagers, hdbOfficers);
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
        hdbManagers = init.setManagerManagedProjects(hdbManagers,projects);

        //Initialize Application info
        ArrayList<Application> applications = init.loadApplicationInfo(applicant, projects);

        
        //General general = new General();
        //general.editProjectFile(projects.get(1),"New Name");
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

        while(!userInput.equals("quit")){
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
                            if(handleManagerLogin(userName, userPassword, hdbManagers, projects, hdbOfficers, applicant)){
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
                        //for now will be calling direct from User class
                        // System.out.println("Enter new password ");
                        // String newPassword = scanner.nextLine();
                        // System.out.println("Enter new password again: ");
                        // while (!newPassword.equals(scanner.nextLine())) {
                        //     System.out.println("!!!Wrong password!!!");
                        //     System.out.println("Enter new password ");
                        //     newPassword = scanner.nextLine();
                        //     System.out.println("Enter new password again: ");
                        // }
                        System.out.println("======Instructions======");
                        System.out.println("1. password");
                        System.out.println("2. name");
                        System.out.println("3. NRIC");
                        System.out.println("4. age");
                        System.out.println("5. marritialStatus(singale/married)");
                        System.out.println("========================");
                        String changeTarget = scanner.nextLine();
                        System.out.println("Enter content:");
                        switch (userType){
                            case "hdb manager":
                                hdbManagers.get(userPos).changeContent(scanner.nextLine(),filePath + "/ManagerList.txt",changeTarget);
                                break;
                            case "hdb officer":
                                hdbOfficers.get(userPos).changeContent(scanner.nextLine(),filePath + "/OfficerList.txt",changeTarget);
                                break;
                            case "applicant":
                                applicant.get(userPos).changeContent(scanner.nextLine(),filePath + "/ApplicantList.txt",changeTarget);
                                break;
                            default:
                                System.out.println("Error occured == could not find user type");
                                break;
                        }
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
                    if (showPostLoginMenu(hdbManagers.get(i))) {
                        ManagerInput manager = new ManagerInput();
                        manager.switchFunction(hdbManagers.get(i).getNRIC(), projects, hdbManagers, hdbOfficers, applicant);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Handles login for HDB Officer
     * 
     * @param userName the username or NRIC
     * @param userPassword the password
     * @param hdbOfficers list of HDB officers
     * @return true if login successful, false otherwise
     */
    public static boolean handleOfficerLogin(String userName, String userPassword, 
                                           ArrayList<HDBOfficer> hdbOfficers) {
        for(int i = 0; i < hdbOfficers.size(); i++){
            if(hdbOfficers.get(i).getName().equals(userName) || hdbOfficers.get(i).getNRIC().equals(userName)){
                if(hdbOfficers.get(i).login(userPassword)){
                    loginSuccess(hdbOfficers.get(i));
                    
                    // Dummy initialization of projects
                    ArrayList<Project> projectList = new ArrayList<Project>();
                    
                    ArrayList<MaritalStatus> allowedGroupsForTwoRoom = new ArrayList<>();
                    allowedGroupsForTwoRoom.add(MaritalStatus.SINGLE);  // Only singles can apply

                    ArrayList<MaritalStatus> allowedGroupsForThreeRoom = new ArrayList<>();
                    allowedGroupsForThreeRoom.add(MaritalStatus.MARRIED);  // Only married couples can apply

                    // Create two room flats
                    FlatType twoRoomFlat1 = new TwoRoom(10, 200000, allowedGroupsForTwoRoom);  // 10 units, 200k price
                    FlatType twoRoomFlat2 = new TwoRoom(5, 180000, allowedGroupsForTwoRoom);   // 5 units, 180k price

                    // Create three room flats
                    FlatType threeRoomFlat1 = new ThreeRoom(8, 300000, allowedGroupsForThreeRoom);  // 8 units, 300k price
                    FlatType threeRoomFlat2 = new TwoRoom(4, 320000, allowedGroupsForThreeRoom);  // 4 units, 320k price

                    // Add these flats to the list
                    ArrayList<FlatType> flatTypes = new ArrayList<>();
                    flatTypes.add(twoRoomFlat1);
                    flatTypes.add(twoRoomFlat2);
                    flatTypes.add(threeRoomFlat1);
                    flatTypes.add(threeRoomFlat2);
                    
                    // Dummy Project 
                    String projectName = "Sunrise Residences";
                    String neighborhood = "Punggol";
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
                    
                    ArrayList<MaritalStatus> allowedGroups = new ArrayList<>();
                    allowedGroups.add(MaritalStatus.SINGLE);
                    
                    Project dummyProject = new Project(
                        projectName,
                        neighborhood,
                        flatTypes,
                        openingDate,
                        closingDate,
                        dummyManager,
                        officerSlots,
                        isVisible,
                        applicants,
                        officers,
                        enquiries,
                        allowedGroups
                    );
                    
                    projectList.add(dummyProject);
                    dummyProject.addHDBOfficer(hdbOfficers.get(i));
                    
                    // Create dummy applicants
                    Applicant applicant1 = new Applicant(
                        "Alice Tan", 
                        "S1234567A", 
                        1, 
                        "password1", 
                        28, 
                        MaritalStatus.SINGLE
                    );

                    Applicant applicant2 = new Applicant(
                        "Bob Lim", 
                        "S7654321B", 
                        2, 
                        "password2", 
                        35, 
                        MaritalStatus.MARRIED
                    );
                    
                    Application app1 = new Application(applicant1, dummyProject, twoRoomFlat1);
                    Application app2 = new Application(applicant2, dummyProject, threeRoomFlat1);

                    applicant1.applyForProject(dummyProject, twoRoomFlat1);
                    applicant2.applyForProject(dummyProject, threeRoomFlat1);
                    applicant1.bookFlat(hdbOfficers.get(i));
                    applicant2.bookFlat(hdbOfficers.get(i));
                    hdbOfficers.get(i).setManagedProject(dummyProject);
                    hdbOfficers.get(i).setManagingOfficer(true);
                    
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
                    
                    // Dummy initialization of projects
                    ArrayList<Project> projectList = new ArrayList<Project>();
                    
                    ArrayList<MaritalStatus> allowedGroupsForTwoRoom = new ArrayList<>();
                    allowedGroupsForTwoRoom.add(MaritalStatus.SINGLE);  // Only singles can apply

                    ArrayList<MaritalStatus> allowedGroupsForThreeRoom = new ArrayList<>();
                    allowedGroupsForThreeRoom.add(MaritalStatus.MARRIED);  // Only married couples can apply

                    // Create two room flats
                    FlatType twoRoomFlat1 = new TwoRoom(10, 200000, allowedGroupsForTwoRoom);  // 10 units, 200k price
                    FlatType twoRoomFlat2 = new TwoRoom(5, 180000, allowedGroupsForTwoRoom);   // 5 units, 180k price

                    // Create three room flats
                    FlatType threeRoomFlat1 = new ThreeRoom(8, 300000, allowedGroupsForThreeRoom);  // 8 units, 300k price
                    FlatType threeRoomFlat2 = new TwoRoom(4, 320000, allowedGroupsForThreeRoom);  // 4 units, 320k price

                    // Add these flats to the list
                    ArrayList<FlatType> flatTypes = new ArrayList<>();
                    flatTypes.add(twoRoomFlat1);
                    flatTypes.add(twoRoomFlat2);
                    flatTypes.add(threeRoomFlat1);
                    flatTypes.add(threeRoomFlat2);
                    
                    // Dummy Project 
                    String projectName = "Sunrise Residences";
                    String neighborhood = "Punggol";
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
                    applicant.get(i).setMaritalStatus(MaritalStatus.SINGLE);
                    
                    ArrayList<MaritalStatus> allowedGroups = new ArrayList<>();
                    allowedGroups.add(MaritalStatus.SINGLE);
                    
                    Project dummyProject = new Project(
                        projectName,
                        neighborhood,
                        flatTypes,
                        openingDate,
                        closingDate,
                        dummyManager,
                        officerSlots,
                        isVisible,
                        applicants,
                        officers,
                        enquiries,
                        allowedGroups
                    );
                    
                    projectList.add(dummyProject);
                    HDBOfficer dummyOfficer = new HDBOfficer("DummyOfficer", "T1234567I", -1, "password", 35, MaritalStatus.MARRIED, dummyProject);
                    dummyProject.addHDBOfficer(dummyOfficer);
                    
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
}