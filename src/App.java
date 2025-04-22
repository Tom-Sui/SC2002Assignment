import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
                    
                    // to update status for all projects
                    HDBManager mainManager = ManagerFactory.defaultManager(); 
                    mainManager.updateMaritalStatus(projects);
                    
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
                            for(int i = 0; i < hdbManagers.size(); i++){
                                if(hdbManagers.get(i).getName().equals(userName) || hdbManagers.get(i).getNRIC().equals(userName)){
                                    if(hdbManagers.get(i).login(userPassword)){
                                        System.out.println("===Login success===");
                                        System.out.println("Welcome " + hdbManagers.get(i).getName());
                                        System.out.println("===================\n");
                                        userPos = i;
                                        currentUserId = userName;
                                        logedIn = true;

                                        ManagerInput manager = new ManagerInput();
                                        manager.switchFunction(hdbManagers.get(i).getNRIC(), projects, hdbManagers, hdbOfficers, applicant);                                        
                                        break;
                                    }
                                }
                            }
                            if(!logedIn){
                                System.out.println("============Login failed============");
                                System.out.println("Incorrect password or incorrect ID");
                                System.out.println("====================================");
                            }

                        case "hdb officer","2":
                            for(int i = 0; i < hdbOfficers.size(); i++){
                                if(hdbOfficers.get(i).getName().equals(userName) || hdbOfficers.get(i).getNRIC().equals(userName)){
                                    if(hdbOfficers.get(i).login(userPassword)){
                                        System.out.println("===Login success===");
                                        System.out.println("Welcome " + hdbOfficers.get(i).getName());
                                        System.out.println("===================\n");
                                        ApplicantOfficerApp.start(hdbOfficers.get(i),projects);
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
      
                                        ApplicantOfficerApp.start(applicant.get(i), projects);
                                        
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
        System.out.println("========================");
    }
}