import java.sql.Date;
import java.util.ArrayList;

public class Project {
    private String projectName;
    private String neighborhood;
    private int twoRoomUnits;
    private int threeRoomUnits;
    private Date applicationOpeningDate;
    private Date applicationClosingDate;
    private HDBManager hdbManager;
    private int availableOfficerSlots;
    private boolean visibility;
    private ArrayList<Applicant> applicants = new ArrayList<Applicant>();
    private ArrayList<HDBOfficer> hdbOfficers = new ArrayList<HDBOfficer>();
    private ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>();
    private MaritalStatus maritalStatus;

    //Wasn't stated in the UML diagram
    private FlateType flateType;
    
    public Project() {};
    public Project(String projectName, String neighborhood, int twoRoomUnits, int threeRoomUnits, Date applicationOpeningDate, Date applicationClosingDate,
    		HDBManager hdbManager, int availableOfficerSlots, boolean visiblity, ArrayList<Applicant> applicants, ArrayList<HDBOfficer> hdbOfficers, ArrayList<Enquiry> enquiries,
    		MaritalStatus maritalStatus) {
    	this.projectName = projectName;
    	this.neighborhood = neighborhood;
    	this.twoRoomUnits = twoRoomUnits;
    	this.threeRoomUnits = threeRoomUnits;
    	this.applicationOpeningDate = applicationOpeningDate;
    	this.applicationClosingDate = applicationClosingDate;
    	this.hdbManager = hdbManager;
    	this.availableOfficerSlots = availableOfficerSlots;
    	this.visibility = visiblity;
    	this.applicants = applicants;
    	this.hdbOfficers = hdbOfficers;
    	this.enquiries = enquiries;
    	this.maritalStatus = maritalStatus;  	
    	
    }
    //Methods
    public boolean getVisibility(){
        return visibility;
    }
    
    public MaritalStatus getMaritalStatus() {
    	return maritalStatus;
    }
    public void addApplicant(Applicant applicant){

    }
    public void removeApplicant(Applicant applicant){

    }
    public void removeHDBOfficer(HDBOfficer hdbOfficer){

    }
    public void addEnquiry(Enquiry enquiry){

    }
    public FlateType getRemainingUnits(FlateType flateType){
        return this.flateType;
    }
    public void decreaseRemainingUnits(FlateType flateType){

    }
    
    public String toString() {
		String projectDetails = String.format("Project Name: %s", projectName);
		return projectDetails;
    	
    }

    

}
