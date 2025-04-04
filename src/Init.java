import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Init {
    public Applicant[] LoadUserInfo(){
        int size;
        int count = 0;

        File applicantFile = new File("./Data/ApplicantList.txt");
        Applicant[] applicant;

        try{
            Scanner scanner = new Scanner(applicantFile);
            size = Integer.parseInt(scanner.nextLine());

            applicant = new Applicant[size];

            while(scanner.hasNextLine()){
                applicant[count] = new Applicant();
                String[] data = scanner.nextLine().split(",");
                applicant[count].setName(data[0]);
                applicant[count].setNRIC(data[1]);
                applicant[count].setage(Integer.parseInt(data[2]));
                if(data[3].equals("Single")){
                    applicant[count].setMatritialSatus(MaritialStatus.maritialStatus.SINGLE);
                }else{
                    applicant[count].setMatritialSatus(MaritialStatus.maritialStatus.MARRIED);
                }
                

                //Hash the password and store back
                applicant[count].setPassword(data[4]);

                count += 1;
            }

            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ApplicantList.txt");
            e.printStackTrace();
            applicant = new Applicant[0];
        }
        return applicant;
    }

    public HDBManager[] LoadManagerInfo(){
        int size;
        int count = 0;

        File managerFile = new File("./Data/ManagerList.txt");
        HDBManager[] hdbManagers;

        try{
            Scanner scanner = new Scanner(managerFile);
            size = Integer.parseInt(scanner.nextLine());

            hdbManagers = new HDBManager[size];

            while(scanner.hasNextLine()){
                hdbManagers[count] = new HDBManager();
                String[] data = scanner.nextLine().split(",");
                hdbManagers[count].setName(data[0]);
                hdbManagers[count].setNRIC(data[1]);
                hdbManagers[count].setage(Integer.parseInt(data[2]));
                if(data[3].equals("Single")){
                    hdbManagers[count].setMatritialSatus(MaritialStatus.maritialStatus.SINGLE);
                }else{
                    hdbManagers[count].setMatritialSatus(MaritialStatus.maritialStatus.MARRIED);
                }
                hdbManagers[count].setPassword(data[4]);

                count += 1;
            }

            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ManagerList.txt");
            e.printStackTrace();
            hdbManagers = new HDBManager[0];
        }
        return hdbManagers;
    }

    public HDBOfficer[] LoadOfficerInfo(){
        int size;
        int count = 0;

        File officerFile = new File("./Data/OfficerList.txt");
        HDBOfficer[] hdbOfficer;
                
        try{
            Scanner scanner = new Scanner(officerFile);
            size = Integer.parseInt(scanner.nextLine());

            hdbOfficer = new HDBOfficer[size];

            while(scanner.hasNextLine()){
                hdbOfficer[count] = new HDBOfficer();
                String[] data = scanner.nextLine().split(",");
                hdbOfficer[count].setName(data[0]);
                hdbOfficer[count].setNRIC(data[1]);
                hdbOfficer[count].setage(Integer.parseInt(data[2]));
                if(data[3].equals("Single")){
                    hdbOfficer[count].setMatritialSatus(MaritialStatus.maritialStatus.SINGLE);
                }else{
                    hdbOfficer[count].setMatritialSatus(MaritialStatus.maritialStatus.MARRIED);
                }
                hdbOfficer[count].setPassword(data[4]);
                count += 1;
            }
            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading OfficerList.txt");
            e.printStackTrace();
            hdbOfficer = new HDBOfficer[0];
        }
        return hdbOfficer;
    }


    public Project[] LoadProjectInfo(HDBManager[] hdbManager, HDBOfficer[] hdbOfficer){
        int size;
        int count = 0;
        File projectFile = new File("./Data/ProjectList.txt");
        Project[] project;

        try{
            Scanner scanner = new Scanner(projectFile);
            size = Integer.parseInt(scanner.nextLine());

            project = new Project[size];
            //To format the LocalData according to txt content
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            while(scanner.hasNextLine()){
                project[count] = new Project();
                String[] data = scanner.nextLine().split(",");

                project[count] = setProject(data,hdbManager,hdbOfficer);

                count += 1;
            }

            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ProjectLists.txt");
            e.printStackTrace();
            return null;
        }
        // project[0].debugOut();
        return project;
    }

    public Project setProject(String[] data,HDBManager[] hdbManager, HDBOfficer[] hdbOfficer){
        General general = new General();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Project project = new Project();
        project.setProjectName(data[0]);
        project.setNeiborhood(data[1]);

        //!!!NOTE!!!
        //If there is any missing content, program may result in error
        //Set flateTypes
        FlateType[] flateType = new FlateType[(data.length-7)/3];
        
        for(int i = 0; i < (data.length-7)/3 - 1; i+=1){
            flateType[i] = new FlateType(data[2+3*i],Integer.parseInt(data[3+3*i]),Integer.parseInt(data[4+3*i]));
        }
        project.setFlateType(flateType);

        //Set closing and opending dates
        project.setApplicationOpeningData(LocalDate.parse(data[data.length - 5],formatter));
        project.setApplicationClosingData(LocalDate.parse(data[data.length - 4],formatter));


        //Assuming that there can only can be one manager per project
        project.setHDBManager(general.findPerson(hdbManager, data[data.length - 3]));

        //set available officer slots
        project.setAvailableOfficerSlots(Integer.parseInt(data[data.length - 2]));

        //Set all included HDB officers
        String[] officerName = data[data.length - 1].split("&");
        
        HDBOfficer[] tempHDBOfficers = new HDBOfficer[officerName.length];
        
        for(int i = 0; i < officerName.length; i++){
            tempHDBOfficers[i] = new HDBOfficer();
            tempHDBOfficers[i] = general.findPerson(hdbOfficer,officerName[i]);
        }
        project.setHDBOfficer(tempHDBOfficers);
        return project;


        // project[count].setProjectName(data[0]);
        // project[count].setNeiborhood(data[1]);

        // //!!!NOTE!!!
        // //If there is any missing content, program may result in error
        // //Set flateTypes
        // FlateType[] flateType = new FlateType[(data.length-7)/3];
        
        // for(int i = 0; i < (data.length-7)/3 - 1; i+=1){
        //     flateType[i] = new FlateType(data[2+3*i],Integer.parseInt(data[3+3*i]),Integer.parseInt(data[4+3*i]));
        // }
        // project[count].setFlateType(flateType);

        // //Set closing and opending dates
        // project[count].setApplicationOpeningData(LocalDate.parse(data[data.length - 5],formatter));
        // project[count].setApplicationClosingData(LocalDate.parse(data[data.length - 4],formatter));


        // //Assuming that there can only can be one manager per project
        // project[count].setHDBManager(findPerson(hdbManager, data[data.length - 3]));

        // //set available officer slots
        // project[count].setAvailableOfficerSlots(Integer.parseInt(data[data.length - 2]));

        // //Set all included HDB officers
        // String[] officerName = data[data.length - 1].split("&");
        
        // HDBOfficer[] tempHDBOfficers = new HDBOfficer[officerName.length];
        
        // for(int i = 0; i < officerName.length; i++){
        //     tempHDBOfficers[i] = new HDBOfficer();
        //     tempHDBOfficers[i] = findPerson(hdbOfficer,officerName[i]);
        // }
        // project[count].setHDBOfficer(tempHDBOfficers);
        
    }

    public HDBManager[] setManagerManagedProjects(HDBManager[] hdbManagers, Project[] projects){
        for(int i = 0; i < hdbManagers.length; i++){
            for(int j = 0; j < projects.length; j++){
                // System.out.println(hdbManagers[i].getName());
                // System.out.println(projects.length);
                //Ensure that the managerlist.txt manager names matches with those in the ManagerList.txt
                try{
                    if(hdbManagers[i].getName().equals(projects[j].getHDBManager().getName())){
                        hdbManagers[i].setManagedProjects(projects[j]);
                    }
                }catch(NullPointerException e){
                    System.out.println("Manager " + projects[j].getHDBManager().getName() + " is not registered manager");
                }
            }
        }
        return hdbManagers;
    }

    // public void setManagedProjects(HDBManager hdbManager, Project project){
    //     hdbManager.setManagedProjects(project);
    // }
}
