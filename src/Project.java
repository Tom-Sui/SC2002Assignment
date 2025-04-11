import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Project {
    private static int projectID = 0;
    private String projectName;
    private String neighborhood;
    private ArrayList<FlatType> flatTypes = new ArrayList<>();
    private Date applicationOpeningDate;
    private Date applicationClosingDate;
    private HDBManager hdbManager;
    private boolean visibility;
    private ArrayList<Applicant> applicants = new ArrayList<>();
    private ArrayList<HDBOfficer> hdbOfficers = new ArrayList<>();
    private ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>();
    private ArrayList<Application> applications = new ArrayList<>();
    private ArrayList<MaritalStatus> allowedGroups = new ArrayList<>()

    public Project() {};
    public Project(String projectName, String neighborhood, ArrayList<FlatType> flatTypes, Date applicationOpeningDate, Date applicationClosingDate,
    		HDBManager hdbManager, boolean visiblity, ArrayList<Applicant> applicants, ArrayList<HDBOfficer> hdbOfficers, ArrayList<Enquiry> enquiries,
    		ArrayList<MaritalStatus> allowedGroups) {
        projectID = projectID++;
    	this.projectName = projectName;
    	this.neighborhood = neighborhood;
    	this.flatTypes = flatTypes;
    	this.applicationOpeningDate = applicationOpeningDate;
    	this.applicationClosingDate = applicationClosingDate;
    	this.hdbManager = hdbManager;
    	this.visibility = visiblity;
    	this.applicants = applicants;
    	this.hdbOfficers = hdbOfficers;
    	this.enquiries = enquiries;
    	this.allowedGroups = allowedGroups;  	
    }

    //set methods
    public void setProjectName(String projectName){
        this.projectName = projectName;
    }
    public void setNeiborhood(String neighborhood){
        this.neighborhood = neighborhood;
    }

    public void setApplicationOpeningDate(Date applicationOpeningDate){
        this.applicationOpeningDate = applicationOpeningDate;
    }
    public void setApplicationClosingDate(Date applicationClosingDate){
        this.applicationClosingDate = applicationClosingDate;
    }

    public void setHDBManager(HDBManager hdbManager){
        this.hdbManager = hdbManager;
    }
    public void setHDBOfficer(ArrayList<HDBOfficer> hdbOfficer){
        this.hdbOfficers = hdbOfficer;
    }
    
    public void setFlatType(ArrayList<FlatType> flatType){
        this.flatTypes = flatType;
    }

    public void setVisibility(boolean visibility){
        this.visibility = visibility;
    }

    //get methods
    public String getProjectName(){
        return this.projectName;
    }

    public String getNeiborhood(){
        return this.neighborhood;
    }

    public Date getApplicationOpeningDate(){
        return this.applicationOpeningDate;
    }
    public Date getApplicationClosingDate(){
        return this.applicationClosingDate;
    }
    public HDBManager getHDBManager(){
        return this.hdbManager;
    }

    public ArrayList<HDBOfficer> getHDBOfficer(){
        return this.hdbOfficers;
    }
    
    public void addHDBOfficer(HDBOfficer hdbOfficer) {
    	hdbOfficers.add(hdbOfficer);
    }
    
    public String toString() {
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
    	String formattedOpeningDate = formatter.format(applicationOpeningDate);
    	String formattedClosingDate = formatter.format(applicationClosingDate);
    	String flatDetails = FlatTypeLogic.displayFlatTypes(flatTypes);
		String projectDetails = String.format("%s, %s, %s to %s\n%s", projectName, neighborhood, formattedOpeningDate, formattedClosingDate, flatDetails);
		return projectDetails;
    	
    }

    public void addApplication(Application application){
        applications.add(application);
    }

    public ArrayList<Application> getApplications(){
        return applications;
    }

    public ArrayList<FlatType> getFlatTypeList(){
        return flatTypes;
    }
    

}