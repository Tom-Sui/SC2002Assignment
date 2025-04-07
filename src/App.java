import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.sql.Date;

public class App{
    //To keep track who is loged in
    private static String filePath = "./Data";
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
        // projects = hdbManagers.get(0).createProject("aNOTHER NAME,TESTING TESTING name,2-Room,2,350000,3-Room,3,450000,12/12/2343,12/12/2343,tom,3,Daniel&Emily",projects,hdbManagers,hdbOfficers);


        //Initialize manager managed projects
        hdbManagers = init.setManagerManagedProjects(hdbManagers,projects);

        //Example of how to edit project
        // hdbManagers.get(1).editProject("new name", "different name", "projectname",hdbManagers,hdbOfficers);

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
                    System.out.println("1. HDB Manger");
                    System.out.println("2. HDB Officier");
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
                        case "hdb officer","2":
                            for(int i = 0; i < hdbManagers.size(); i++){
                                if(hdbManagers.get(i).getName().equals(userName) || hdbManagers.get(i).getNRIC().equals(userName)){
                                    if(hdbManagers.get(i).login(userPassword)){
                                        System.out.println("===Login success===");
                                        System.out.println("Welcome " + hdbManagers.get(i).getName());
                                        System.out.println("===================\n");
                                        userPos = i;
                                        currentUserId = userName;
                                        logedIn = true;
                                        break;
                                    }
                                }
                            }
                            if(!logedIn){
                                System.out.println("============Login failed============");
                                System.out.println("Incorrect password or incorrect ID");
                                System.out.println("====================================");
                            }

                        case "hdb officier":
                            for(int i = 0; i < hdbOfficers.size(); i++){
                                if(hdbOfficers.get(i).getName().equals(userName) || hdbOfficers.get(i).getNRIC().equals(userName)){
                                    if(hdbOfficers.get(i).login(userPassword)){
                                        System.out.println("===Login success===");
                                        System.out.println("Welcome " + hdbOfficers.get(i).getName());
                                        System.out.println("===================\n");
                                        userPos = i;
                                        currentUserId = userName;
                                        logedIn = true;
                                        break;
                                    }
                                }
                            }
                            if(!logedIn){
                                System.out.println("============Login failed============");
                                System.out.println("Incorrect password or incorrect ID");
                                System.out.println("====================================");
                            }

                        case "applicant","3":
                            for(int i = 0; i < applicant.size(); i++){
                                if(applicant.get(i).getName().equals(userName) || applicant.get(i).getNRIC().equals(userName)){
                                    if(applicant.get(i).login(userPassword)){
                                        System.out.println("===Login success===");
                                        System.out.println("Welcome " + applicant.get(i).getName());
                                        System.out.println("===================\n");
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
                                        
                                        MaritalStatus maritalStatus = MaritalStatus.SINGLE;
                                        
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
                                            maritalStatus
                                        );
                                        
                                        projectList.add(dummyProject);
                                        HDBOfficer dummyOfficer = new HDBOfficer("DummyOfficer", "T1234567I", "DummyOfficer", "password", 35, MaritalStatus.MARRIED, dummyProject);
                                        dummyProject.addHDBOfficer(dummyOfficer);
                                        // Launch applicant interface
                                        ApplicantApp applicantApp = new ApplicantApp();
                                        applicantApp.start(applicant.get(i), projectList);
                                        
                                        userPos = i;
                                        currentUserId = userName;
                                        logedIn = true;
                                        break;
                                    }
                                }
                            }
                            if(!logedIn){
                                System.out.println("============Login failed============");
                                System.out.println("Incorrect password or incorrect ID");
                                System.out.println("====================================");
                            }
                            // logedIn = false;
                            // userType = "NULL";
                            // userName = "NULL";
                            // userPos = -1;
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
        System.out.println("9. quit");
        System.out.println("========================");
    }
}