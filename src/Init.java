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

/**
 * The Init class provides methods for loading and initializing application data
 * from various text files. It handles loading of applicant, manager, officer,
 * and project information.
 */
public class Init {
	
	private String DataFilePath = "./Data";   // TO ADD /src/ FOR ECLIPSE
    /**
     * Loads applicant information from the ApplicantList.txt file.
     * 
     * @return ArrayList of Applicant objects populated with data from the file
     */
    public ArrayList<Applicant> LoadUserInfo() {
        File applicantFile = new File(DataFilePath + "/ApplicantList.txt");
        ArrayList<Applicant> applicants = new ArrayList<Applicant>();
        Applicant applicant;
        try {
            Scanner scanner = new Scanner(applicantFile);

            while (scanner.hasNextLine()) {
                applicant = new Applicant();
                String[] data = scanner.nextLine().split(",");
                applicant.setUserID(Integer.parseInt(data[0]));
                applicant.setName(data[1]);
                applicant.setNRIC(data[2]);
                applicant.setage(Integer.parseInt(data[3]));
                if (data[4].equals("Single")) {
                    applicant.setMaritalStatus(MaritalStatus.SINGLE);
                } else {
                    applicant.setMaritalStatus(MaritalStatus.MARRIED);
                }
                applicant.setPassword(data[5]);
                applicants.add(applicant);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occured while reading ApplicantList.txt");
            e.printStackTrace();
        }
        return applicants;
    }

    /**
     * Loads HDB manager information from the ManagerList.txt file.
     * 
     * @return ArrayList of HDBManager objects populated with data from the file
     */
    public ArrayList<HDBManager> LoadManagerInfo() {
        File managerFile = new File(DataFilePath + "/ManagerList.txt");
        ArrayList<HDBManager> hdbManagers = new ArrayList<HDBManager>();
        HDBManager hdbManager;
        try {
            Scanner scanner = new Scanner(managerFile);

            while (scanner.hasNextLine()) {
                hdbManager = ManagerFactory.defaultManager();
                String[] data = scanner.nextLine().split(",");
                hdbManager.setUserID(Integer.parseInt(data[0]));
                hdbManager.setName(data[1]);
                hdbManager.setNRIC(data[2]);
                hdbManager.setage(Integer.parseInt(data[3]));
                if (data[4].equals("Single")) {
                    hdbManager.setMaritalStatus(MaritalStatus.SINGLE);
                } else {
                    hdbManager.setMaritalStatus(MaritalStatus.MARRIED);
                }
                hdbManager.setPassword(data[5]);
                hdbManagers.add(hdbManager);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occured while reading ManagerList.txt");
            e.printStackTrace();
        }
        return hdbManagers;
    }

    /**
     * Loads HDB officer information from the OfficerList.txt file.
     * 
     * @return ArrayList of HDBOfficer objects populated with data from the file
     */
    public ArrayList<HDBOfficer> LoadOfficerInfo() {
        File officerFile = new File(DataFilePath + "/OfficerList.txt");
        ArrayList<HDBOfficer> hdbOfficers = new ArrayList<HDBOfficer>();
        HDBOfficer hdbOfficer; // Declare the variable here

        try {
            Scanner scanner = new Scanner(officerFile);

            while (scanner.hasNextLine()) {
                hdbOfficer = new HDBOfficer(); // Initialize the variable
                String[] data = scanner.nextLine().split(",");
                hdbOfficer.setUserID(Integer.parseInt(data[0]));
                hdbOfficer.setName(data[1]);
                hdbOfficer.setNRIC(data[2]);
                hdbOfficer.setage(Integer.parseInt(data[3]));
                if (data[4].equals("Single")) {
                    hdbOfficer.setMaritalStatus(MaritalStatus.SINGLE);
                } else {
                    hdbOfficer.setMaritalStatus(MaritalStatus.MARRIED);
                }
                hdbOfficer.setPassword(data[5]);
                hdbOfficers.add(hdbOfficer);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading OfficerList.txt");
            e.printStackTrace();
        }
        return hdbOfficers;
    }

    /**
     * Loads project information from the ProjectList.txt file and associates
     * managers and officers with each project.
     * 
     * @param hdbManager List of HDB managers to associate with projects
     * @param hdbOfficer List of HDB officers to associate with projects
     * @return ArrayList of Project objects populated with data from the file
     */
    public ArrayList<Project> LoadProjectInfo(ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer) {
        File projectFile = new File(DataFilePath + "/ProjectList.txt");
        ArrayList<Project> projects = new ArrayList<Project>();
        try {
            Scanner scanner = new Scanner(projectFile);

            Project project;
            while (scanner.hasNextLine()) {
                project = new Project();
                String[] data = scanner.nextLine().split(",");
                project = setProject(data, hdbManager, hdbOfficer);
                projects.add(project);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occured while reading ProjectLists.txt");
            e.printStackTrace();
            return null;
        }

        return projects;
    }
    
    /**
     * Loads application information from the ApplicationList.txt file and associates
     * applicants and projects with each application.
     * 
     * @param applicants List of applicants to associate with applications
     * @param projects List of projects to associate with applications
     * @return ArrayList of Application objects populated with data from the file
     */
    public ArrayList<Application> loadApplicationInfo(ArrayList<Applicant> applicants, ArrayList<Project> projects){
        File applicationFile = new File(DataFilePath + "/ApplicationList.txt");
        ArrayList<Application> applications = new ArrayList<Application>();
        try {
            Scanner scanner = new Scanner(applicationFile);

            while (scanner.hasNextLine()) {
                Application application = new Application();
                String[] data = scanner.nextLine().split(",");
                application.setApplicant(ApplicationLogic.getApplicant(applicants, data[1]));
                application.setProject(ApplicationLogic.getProject(projects, data[2]));
                application.setApplicationStatus(ApplicationStatus.valueOf(data[3]));
                application.setBookingRequested(Boolean.parseBoolean(data[4]));
                application.setIsBooked(Boolean.parseBoolean(data[5]));
                application.setFlatType(ApplicationLogic.getFlatType(application.getProject(), data[6]));
                applications.add(application);     
                /**	
                 * Insert all applications to the applicant's past application list temporarily for simplicity.
                 * We will check and update accordingly with another method.
                 */
                Applicant applicant = application.getApplicant();
                applicant.appendPastApplications(application);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occured while reading ApplicationList.txt");
            e.printStackTrace();
        }
        return applications;
    }

    /**
     * Loads booking information from the BookingList.txt file and associates
     * applications and officers with each booking.
     * 
     * @param applications List of applications to associate with bookings
     * @param officers List of HDB officers to associate with bookings
     */
    public void loadBookingInfo(ArrayList<Application> applications, ArrayList<HDBOfficer> officers){
        File applicationFile = new File(DataFilePath + "/BookingList.txt");
        try {
            Scanner scanner = new Scanner(applicationFile);

            while (scanner.hasNextLine()) {
                Booking booking = new Booking();
                String[] data = scanner.nextLine().split(",");
                booking.setApplicationId(Integer.parseInt(data[1]));
                booking.setOfficerNRIC((data[2]));
                
                
                for (Application application : applications) {
                    if (application.getApplicationId() == booking.getApplicationId()) {
                        for (HDBOfficer hdbOfficer : officers) {
                            if (hdbOfficer.getNRIC().equals(booking.getOfficerNRIC())) {
                            	hdbOfficer.addManagedApplications(application);
                                break; 
                            }
                        }
                    }
                }          		

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occured while reading BookingList.txt");
            e.printStackTrace();
        }
    }

    public ArrayList<HDBOfficer> loadOfficerRegistrationInfo(ArrayList<HDBOfficer> hdbOfficers, ArrayList<Project> projects) {
        File officerFile = new File(DataFilePath + "/OfficerRegistrationList.txt");
        try {
            Scanner scanner = new Scanner(officerFile);

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(","); // Parse the line into parts
                String officerNRIC = parts[0].trim();
                String projectName = parts[1].trim();
                String registrationStatus = parts[2].trim();
                OfficerRegistrationStatus status = OfficerRegistrationStatus.valueOf(registrationStatus.toUpperCase());
                Project project = General.findProject(projects, projectName);
                HDBOfficer hdbOfficer = General.findOfficer(hdbOfficers, officerNRIC);

                if (hdbOfficer != null && project != null) {
                    hdbOfficer.setOfficerRegistrationStatus(project, status);
                } 
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading OfficerRegistrationList.txt");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid registration status found in file.");
            e.printStackTrace();
        }
        return hdbOfficers;
    }
    
    /**
     * Creates and configures a Project object from raw data.
     * 
     * @param data String array containing project data fields
     * @param hdbManager List of HDB managers for association
     * @param hdbOfficer List of HDB officers for association
     * @return Configured Project object, or null if manager is not found
     */
    public Project setProject(String[] data, ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer) {
        SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
        Project project = new Project();
        project.setProjectName(data[0]);
        project.setNeiborhood(data[1]);

        if (General.findManager(hdbManager, data[data.length - 4]) == null) {
            System.out.println("No such manager found");
            System.out.println("Return null");
            return null;
        }

        // Set flatTypes
        ArrayList<FlatType> flatTypes = new ArrayList<FlatType>();

        TwoRoom twoRoom;
        ThreeRoom threeRoom;

        for (int i = 0; i < (data.length - 7) / 3; i += 1) {
            if (data[2 + 3 * i].equals("2-Room")) {
                ArrayList<MaritalStatus> maritalStatus = new ArrayList<>();
                maritalStatus.add(MaritalStatus.SINGLE);
                twoRoom = new TwoRoom(Integer.parseInt(data[3 + 3 * i]), Double.parseDouble(data[4 + 3 * i]), maritalStatus);
                flatTypes.add(twoRoom);
            }

            if (data[5 + 3 * i].equals("3-Room")) {
                ArrayList<MaritalStatus> maritalStatus = new ArrayList<>();
                maritalStatus.add(MaritalStatus.SINGLE);
                maritalStatus.add(MaritalStatus.MARRIED);
                threeRoom = new ThreeRoom(Integer.parseInt(data[6 + 3 * i]), Double.parseDouble(data[7 + 3 * i]), maritalStatus);
                flatTypes.add(threeRoom);
            }
        }
        project.setFlatType(flatTypes);

        // Set dates
        try {
            project.setApplicationOpeningDate(formatter.parse(data[data.length - 6]));
            project.setApplicationClosingDate(formatter.parse(data[data.length - 5]));
        } catch (ParseException e) {
            System.out.println("Error occured when parse String to Date");
            e.printStackTrace();
        }

        project.setHDBManager(General.findManager(hdbManager, data[data.length - 4]));
        project.setAvailableOfficerSlots(Integer.parseInt(data[data.length - 3]));

        // Set officers
        String[] officerName = data[data.length - 2].split("&");
        ArrayList<HDBOfficer> hdbOfficers = new ArrayList<HDBOfficer>();
        for (int i = 0; i < officerName.length; i++) {
            HDBOfficer tempHDBOfficer = new HDBOfficer();
            tempHDBOfficer = General.findOfficer(hdbOfficer, officerName[i]);
            hdbOfficers.add(tempHDBOfficer);
        }
        project.setHDBOfficer(hdbOfficers);

        if (data[data.length - 1].equals("true")) {
            project.setVisibility(true);
        } else {
            project.setVisibility(false);
        }

        return project;
    }

    /**
     * Associates projects with their respective managers by setting the managed projects
     * field for each manager.
     * 
     * @param hdbManagers List of HDB managers to update
     * @param projects List of projects to associate
     * @return Updated list of HDB managers with managed projects set
     */
    public ArrayList<HDBManager> setManagerManagedProjects(ArrayList<HDBManager> hdbManagers, ArrayList<Project> projects) {
        for (int i = 0; i < hdbManagers.size(); i++) {
            for (int j = 0; j < projects.size(); j++) {
                try {
                    if (hdbManagers.get(i).getNRIC().equals(projects.get(j).getHDBManager().getNRIC())) {
                        hdbManagers.get(i).setManagedProjects(projects.get(j));
                    }
                } catch (NullPointerException e) {
                    System.out.println("Manager " + projects.get(j).getHDBManager().getName() + " is not registered manager");
                }
            }
        }
        return hdbManagers;
    }
    /**
     * Associates projects with their respective officers by setting the managed projects
     * field for each officer.
     * 
     * @param officers List of HDB officers to update
     * @param projects List of projects to associate
     */
    public void setOfficerManagedProjects(ArrayList<HDBOfficer> officers, ArrayList<Project> projects) {
        for (Project project : projects) {
            ArrayList<HDBOfficer> managingOfficers = project.getHDBOfficer();

            for (HDBOfficer managingOfficer : managingOfficers) {
                for (HDBOfficer officer : officers) {
                    if (officer.equals(managingOfficer)) {
                        officer.setManagedProject(project); 
                        officer.setManagingOfficer(true);
                        break; 
                    }
                }
            }
        }
    }

    
     
    /**
     * Loads enquiry information from the EnquiryList.txt file.
     * 
     * @param applicants List of applicants to associate with enquiries
     * @param projects List of projects to associate with enquiries
     * @return ArrayList of Enquiry objects populated with data from the file
     */
    
    public ArrayList<Enquiry> loadEnquiryInfo(ArrayList<Applicant> applicants, ArrayList<Project> projects){
        File enquiryFile = new File("./Data/EnquiryList.txt");
        ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>();
        Enquiry enquiry;
        try {
            Scanner scanner = new Scanner(enquiryFile);

            while (scanner.hasNextLine()){
                enquiry = new Enquiry();
                String[] data = scanner.nextLine().split(",");
                enquiry.setEnquiryID(Integer.parseInt(data[0]));
                enquiry.setUserNric(data[1]);
                enquiry.setProjectName(data[2]);
                enquiry.setMessage(data[3]);
                enquiry.setReplyID(Integer.parseInt(data[4]));
                enquiries.add(enquiry);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occured while reading EnquiryList.txt");
            e.printStackTrace();
        }
        return enquiries;
    }

}
