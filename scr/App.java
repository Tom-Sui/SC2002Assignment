import java.util.Scanner;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;

public class App {
    //To keep track who is loged in
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
        // System.exit(0);
        //Initialize Applicant info int Applicant class
        Applicant[] applicant;
        applicant = init.LoadUserInfo();

        //Initialize HDB officier info into HDBOfficier class
        HDBManager[] hdbManagers;
        hdbManagers = init.LoadManagerInfo();

        //Initialize HDB manager info into HDBManager class
        HDBOfficer[] hdbOfficers;
        hdbOfficers = init.LoadOfficerInfo();

        //Initialize projects info into Project class
        Project[] projects;
        projects = init.LoadProjectInfo(hdbManagers, hdbOfficers);

        // projects = hdbManagers[0].createProject("projects",projects,hdbManagers,hdbOfficers);
        // System.out.println(projects[1].getAvailableOfficerSlots());
        

        //Initialize manager managed projects
        hdbManagers = init.setManagerManagedProjects(hdbManagers,projects);
        // System.out.println(hdbManagers[1].getProject()[1].getProjectName());
        // System.exit(0);

        //return helpinfo (cmds)
        helpInfo();
        System.out.print("Enter cmd: ");
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
                    System.out.println("1. HDB Manger");
                    System.out.println("2. HDB Officier");
                    System.out.println("3. Applicant");
                    System.out.println("===============");
                    userType = "NULL";
                    while(true){
                        if(userType.equals("hdb manager") || 
                        userType.equals("hdb officier") || 
                        userType.equals("applicant")){
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
                        case "hdb officer":
                            for(int i = 0; i < hdbManagers.length; i++){
                                if(hdbManagers[i].getName().equals(userName) || hdbManagers[i].getNRIC().equals(userName)){
                                    if(hdbManagers[i].login(userPassword)){
                                        System.out.println("===Login success===");
                                        System.out.println("Welcome " + hdbManagers[i].getName());
                                        System.out.println("===================\n");
                                        userPos = i;
                                        currentUserId = userName;
                                        logedIn = true;
                                        break;
                                    }else{
                                        if(!logedIn){
                                            System.out.println("============Login failed============");
                                            System.out.println("Incorrect password or incorrect ID");
                                            System.out.println("====================================");
                                        }
                                    }
                                }
                            }

                        case "hdb officier":
                            for(int i = 0; i < hdbOfficers.length; i++){
                                if(hdbOfficers[i].getName().equals(userName) || hdbOfficers[i].getNRIC().equals(userName)){
                                    if(hdbOfficers[i].login(userPassword)){
                                        System.out.println("===Login success===");
                                        System.out.println("Welcome " + hdbOfficers[i].getName());
                                        System.out.println("===================\n");
                                        userPos = i;
                                        currentUserId = userName;
                                        logedIn = true;
                                        break;
                                    }else{
                                        if(!logedIn){
                                            System.out.println("============Login failed============");
                                            System.out.println("Incorrect password or incorrect ID");
                                            System.out.println("====================================");
                                        }
                                    }
                                }
                            }

                        case "applicant":
                            for(int i = 0; i < applicant.length; i++){
                                if(applicant[i].getName().equals(userName) || applicant[i].getNRIC().equals(userName)){
                                    if(applicant[i].login(userPassword)){
                                        System.out.println("===Login success===");
                                        System.out.println("Welcome " + applicant[i].getName());
                                        System.out.println("===================\n");
                                        userPos = i;
                                        currentUserId = userName;
                                        logedIn = true;
                                        break;
                                    }else{
                                        if(!logedIn){
                                            System.out.println("============Login failed============");
                                            System.out.println("Incorrect password or incorrect ID");
                                            System.out.println("====================================");
                                        }
                                    }
                                }
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
                                hdbManagers[userPos].changeContent(scanner.nextLine(),"./Data/ManagerList.txt",changeTarget);
                                break;
                            case "hdb officer":
                                hdbOfficers[userPos].changeContent(scanner.nextLine(),"./Data/OfficerList.txt",changeTarget);
                                break;
                            case "applicant":
                                applicant[userPos].changeContent(scanner.nextLine(),"./Data/ApplicantList.txt",changeTarget);
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