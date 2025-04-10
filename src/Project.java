import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Project {
    private String projectName;
    private String neighborhood;
    //private int twoRoomUnits;
    //private int threeRoomUnits;
    private ArrayList<FlatType> flatTypes = new ArrayList<FlatType>();
    private Date applicationOpeningDate;
    private Date applicationClosingDate;
    private HDBManager hdbManager;
    private int availableOfficerSlots;
    private boolean visibility;
    private ArrayList<Applicant> applicants = new ArrayList<Applicant>();
    private ArrayList<HDBOfficer> hdbOfficers = new ArrayList<HDBOfficer>();
    private ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>();
    private ArrayList<Application> applications = new ArrayList<Application>();
    private MaritalStatus maritalStatus = null;

    //Wasn't stated in the UML diagram
    // private FlateType flateType;
    
    public Project() {};
    public Project(String projectName, String neighborhood, ArrayList<FlatType> flatTypes, Date applicationOpeningDate, Date applicationClosingDate,
    		HDBManager hdbManager, int availableOfficerSlots, boolean visiblity, ArrayList<Applicant> applicants, ArrayList<HDBOfficer> hdbOfficers, ArrayList<Enquiry> enquiries,
    		MaritalStatus maritalStatus) {
    	this.projectName = projectName;
    	this.neighborhood = neighborhood;
    	this.flatTypes = flatTypes;
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
    public void setAvailableOfficerSlots(int availableOfficerSlots){
        this.availableOfficerSlots = availableOfficerSlots;
    }
    public void setFlatType(ArrayList<FlatType> flatType){
        this.flatTypes = flatType;
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
    public int getAvailableOfficerSlots(){
        return this.availableOfficerSlots;
    }

    //Debug method
    // public void debugOut(){
    //     System.out.println(this.projectName);
    //     System.out.println(this.neiborhood);
    //     System.out.println(this.flateType[0].getFlateType());
    //     System.out.println(this.applicationOpeningData);
    //     System.out.println(this.applicationClosingData);
    //     System.out.println(this.hdbManager.getName());
    //     System.out.println(this.hdbOfficer[0].getName());
    // }


    //Methods
    public boolean getVisibility(){
        return visibility;
    }
    
    public MaritalStatus getMaritalStatus() {
    	return maritalStatus;
    }
    
    public ArrayList<FlatType> getFlatTypes(){ return flatTypes; }
    
    public void addApplicant(Applicant applicant){

    }
    public void removeApplicant(Applicant applicant){

    }
    
    public void addHDBOfficer(HDBOfficer hdbOfficer) {
    	hdbOfficers.add(hdbOfficer);
    }
    public void removeHDBOfficer(HDBOfficer hdbOfficer){

    }
    public void addEnquiry(Enquiry enquiry){

    }
//    public FlateType getRemainingUnits(FlateType flateType){
//        return FlateType;
//    }
    public void decreaseRemainingUnits(FlatType flatType){

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