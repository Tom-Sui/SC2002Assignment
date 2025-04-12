import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Init {
    public ArrayList<Applicant> LoadUserInfo(){
        // int count = 0;

        File applicantFile = new File("./Data/ApplicantList.txt");
        ArrayList<Applicant> applicants = new ArrayList<Applicant>();
        Applicant applicant;
        try{
            Scanner scanner = new Scanner(applicantFile);
            // int size = Integer.parseInt(scanner.nextLine());

            while(scanner.hasNextLine()){
                applicant = new Applicant();
                String[] data = scanner.nextLine().split(",");
                applicant.setUserID(Integer.parseInt(data[0]));
                applicant.setName(data[1]);
                applicant.setNRIC(data[2]);
                applicant.setage(Integer.parseInt(data[3]));
                if(data[4].equals("Single")){
                    applicant.setMaritalStatus(MaritalStatus.SINGLE);
                }else{
                    applicant.setMaritalStatus(MaritalStatus.MARRIED);
                }

                //Hash the password and store back
                applicant.setPassword(data[5]);
                applicants.add(applicant);
                // count += 1;
            }

            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ApplicantList.txt");
            e.printStackTrace();
        }
        return applicants;
    }

    public ArrayList<HDBManager> LoadManagerInfo(){

        File managerFile = new File("./Data/ManagerList.txt");
        ArrayList<HDBManager> hdbManagers = new ArrayList<HDBManager>();
        HDBManager hdbManager;
        try{
            Scanner scanner = new Scanner(managerFile);
            // int size = Integer.parseInt(scanner.nextLine());

            while(scanner.hasNextLine()){
                hdbManager = new HDBManager();
                String[] data = scanner.nextLine().split(",");
                hdbManager.setName(data[0]);
                hdbManager.setNRIC(data[1]);
                hdbManager.setage(Integer.parseInt(data[2]));
                if(data[3].equals("Single")){
                    hdbManager.setMaritalStatus(MaritalStatus.SINGLE);
                }else{
                    hdbManager.setMaritalStatus(MaritalStatus.MARRIED);
                }
                hdbManager.setPassword(data[4]);

                hdbManagers.add(hdbManager);

                // count += 1;
            }

            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ManagerList.txt");
            e.printStackTrace();
        }
        return hdbManagers;
    }

    public ArrayList<HDBOfficer> LoadOfficerInfo(){

        File officerFile = new File("./Data/OfficerList.txt");
        ArrayList<HDBOfficer> hdbOfficers = new ArrayList<HDBOfficer>();
        HDBOfficer hdbOfficer;
        try{
            Scanner scanner = new Scanner(officerFile);
            // int size = Integer.parseInt(scanner.nextLine());

            while(scanner.hasNextLine()){
                hdbOfficer = new HDBOfficer();
                String[] data = scanner.nextLine().split(",");
                hdbOfficer.setName(data[0]);
                hdbOfficer.setNRIC(data[1]);
                hdbOfficer.setage(Integer.parseInt(data[2]));
                if(data[3].equals("Single")){
                    hdbOfficer.setMaritalStatus(MaritalStatus.SINGLE);
                }else{
                    hdbOfficer.setMaritalStatus(MaritalStatus.MARRIED);
                }
                hdbOfficer.setPassword(data[4]);

                hdbOfficers.add(hdbOfficer);

                // count += 1;
            }
            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading OfficerList.txt");
            e.printStackTrace();
        }
        return hdbOfficers;
    }


    public ArrayList<Project> LoadProjectInfo(ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer){
        File projectFile = new File("./Data/ProjectList.txt");
        // Project[] project;
        ArrayList<Project> projects = new ArrayList<Project>();
        try{
            Scanner scanner = new Scanner(projectFile);

            Project project;
            while(scanner.hasNextLine()){
                project = new Project();
                String[] data = scanner.nextLine().split(",");

                project = setProject(data,hdbManager,hdbOfficer);
                projects.add(project);
            }

            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ProjectLists.txt");
            e.printStackTrace();
            return null;
        }


        // project[0].debugOut();
        return projects;
    }

    public Project setProject(String[] data,ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer){
        General general = new General();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Project project = new Project();
        project.setProjectName(data[0]);
        project.setNeiborhood(data[1]);
        if(general.findManager(hdbManager, data[data.length - 4]) == null){
            System.out.println("No such manager found: " + data[data.length - 4]);
            return null;
        }

        //!!!NOTE!!!
        //If there is any missing content, program may result in error
        //Set flateTypes
        // ArrayList<flatType> flatTypes = new FlatType[(data.length-7)/3];
        ArrayList<FlatType> flatTypes = new ArrayList<FlatType>();

        TwoRoom twoRoom;
        ThreeRoom threeRoom;

        for(int i = 0; i < (data.length-7)/3 - 1; i+=1){
            if(data[2+3*i].equals("ThreeRoom")){
                ArrayList<MaritalStatus> maritalStatus = new ArrayList<>();
                maritalStatus.add(MaritalStatus.SINGLE);
                twoRoom = new TwoRoom(Integer.parseInt(data[3+3*i]),Double.parseDouble(data[4+3*i]),maritalStatus);
                flatTypes.add(twoRoom);
            }else{
                ArrayList<MaritalStatus> maritalStatus = new ArrayList<>();
                maritalStatus.add(MaritalStatus.SINGLE);
                maritalStatus.add(MaritalStatus.MARRIED);
                threeRoom = new ThreeRoom(Integer.parseInt(data[3+3*i]),Double.parseDouble(data[4+3*i]),maritalStatus);
                flatTypes.add(threeRoom);
            }
        }
        project.setFlatType(flatTypes);

        //Set closing and opending dates
        // project.setApplicationOpeningData(Date.parse(data[data.length - 5],formatter));
        // project.setApplicationClosingData(Date.parse(data[data.length - 4],formatter));
        try{
            project.setApplicationOpeningDate(formatter.parse(data[data.length - 6]));
            project.setApplicationClosingDate(formatter.parse(data[data.length - 5]));
        }catch(ParseException e){
            System.out.println("Error occured when parse String to Date");
            e.printStackTrace();
        }

        //Assuming that there can only can be one manager per project
        project.setHDBManager(general.findManager(hdbManager, data[data.length - 4]));

        //set available officer slots
        project.setAvailableOfficerSlots(Integer.parseInt(data[data.length - 3]));

        //Set all included HDB officers
        String[] officerName = data[data.length - 2].split("&");
        
        ArrayList<HDBOfficer> hdbOfficers = new ArrayList<HDBOfficer>();
        // HDBOfficer[] tempHDBOfficers = new HDBOfficer[officerName.length];
        
        for(int i = 0; i < officerName.length; i++){
            HDBOfficer tempHDBOfficer = new HDBOfficer();
            tempHDBOfficer = general.findOfficer(hdbOfficer,officerName[i]);
            hdbOfficers.add(tempHDBOfficer);
        }
        project.setHDBOfficer(hdbOfficers);

        if(data[data.length - 1].equals("true")){
            project.setVisibility(true);
        }else{
            project.setVisibility(false);
        }

        return project;
        
    }

    public ArrayList<HDBManager> setManagerManagedProjects(ArrayList<HDBManager> hdbManagers, ArrayList<Project> projects){
        for(int i = 0; i < hdbManagers.size(); i++){
            for(int j = 0; j < projects.size(); j++){
                try{
                    if(hdbManagers.get(i).getNRIC().equals(projects.get(j).getHDBManager().getNRIC())){
                        hdbManagers.get(i).setManagedProjects(projects.get(j));
                    }
                }catch(NullPointerException e){
                    System.out.println("Manager " + projects.get(j).getHDBManager().getName() + " is not registered manager");
                }
            }
        }
        return hdbManagers;
    }

    // public void setManagedProjects(HDBManager hdbManager, Project project){
    //     hdbManager.setManagedProjects(project);
    // }

    /**
     * Loads application information from a file and creates Application objects.
     * 
     * @param applicants List of all available applicants
     * @param projects List of all available projects
     * @return ArrayList of Application objects loaded from the file
     * @throws IllegalArgumentException if the input lists are null
     * @throws IllegalStateException if the application file cannot be read or has invalid data
     */
    // public ArrayList<Application> LoadApplicationInfo(ArrayList<Applicant> applicants, ArrayList<Project> projects) {
    //     if (applicants == null || projects == null) {
    //         throw new IllegalArgumentException("Applicants and projects lists cannot be null");
    //     }

    //     File applicationFile = new File("./Data/ApplicationList.txt");
    //     ArrayList<Application> applications = new ArrayList<>();
    //     General general = new General();

    //     try (Scanner scanner = new Scanner(applicationFile)) {
    //         while (scanner.hasNextLine()) {
    //             String line = scanner.nextLine().trim();
    //             if (line.isEmpty()) {
    //                 continue; // Skip empty lines
    //             }

    //             String[] data = line.split(",");
    //             if (data.length < 4) {
    //                 throw new IllegalStateException("Invalid data format in ApplicationList.txt: " + line);
    //             }

    //             Application application = new Application();
                
    //             // Find and set applicant
    //             Applicant applicant = general.findApplicant(applicants, data[0]);
    //             if (applicant == null) {
    //                 throw new IllegalStateException("Applicant not found: " + data[0]);
    //             }
    //             application.setApplicant(applicant);

    //             // Find and set project
    //             Project project = general.findProject(projects, data[1]);
    //             if (project == null) {
    //                 throw new IllegalStateException("Project not found: " + data[1]);
    //             }
    //             application.setProject(project);

    //             // Find and set flat type
    //             FlatType flatType = general.findFlatType(projects, data[1], data[2]);
    //             if (flatType == null) {
    //                 throw new IllegalStateException("Flat type not found for project: " + data[1] + ", type: " + data[2]);
    //             }
    //             application.setFlatType(flatType);

    //             // Set application status
    //             try {
    //                 application.setApplicationStatus(ApplicationStatus.valueOf(data[3]));
    //             } catch (IllegalArgumentException e) {
    //                 throw new IllegalStateException("Invalid application status: " + data[3]);
    //             }

    //             applications.add(application);
    //         }
    //     } catch (FileNotFoundException e) {
    //         throw new IllegalStateException("ApplicationList.txt file not found", e);
    //     } catch (Exception e) {
    //         throw new IllegalStateException("Error reading ApplicationList.txt", e);
    //     }

    //     return applications;
    // }
}
