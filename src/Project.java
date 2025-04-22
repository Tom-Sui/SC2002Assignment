import java.util.Date;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Represents a HDB housing project and various methods.
 * <p>
 * This class encapsulates all properties and behaviors of a housing project,
 * including flat availability, application periods, management team, and
 * applicant tracking.
 * </p>
 * 
 */
public class Project {
    private String projectName;
    private String neighborhood;
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
    private ArrayList<MaritalStatus> allowedGroups = new ArrayList<>();
    /**
     * Default constructor for Project.
     */
    public Project() {};
    
    public Project(Project other) {
        this.projectName = other.projectName;
        this.neighborhood = other.neighborhood;

        // Deep copy of flatTypes
        this.flatTypes = new ArrayList<>(other.getFlatTypes());
        // Deep copy of dates (Date is mutable, so copy needed)
        this.applicationOpeningDate = new Date(other.applicationOpeningDate.getTime());
        this.applicationClosingDate = new Date(other.applicationClosingDate.getTime());

        this.hdbManager = other.hdbManager; // Assuming this is shared (shallow copy)
        this.availableOfficerSlots = other.availableOfficerSlots;
        this.visibility = other.visibility;

        // Shallow copy: Assuming Applicants, Officers, Enquiries, Applications are managed elsewhere
        this.applicants = new ArrayList<>(other.applicants);
        this.hdbOfficers = new ArrayList<>(other.hdbOfficers);
        this.enquiries = new ArrayList<>(other.enquiries);
        this.applications = new ArrayList<>(other.applications);

        // Shallow copy is likely fine for enums like MaritalStatus
        this.allowedGroups = new ArrayList<>(other.allowedGroups);
    }
    
    /**
     * Constructs a Project with full details.
     *
     * @param projectName Name of the project
     * @param neighborhood Name of the neighborhood
     * @param flatTypes List of available flat types
     * @param applicationOpeningDate Start date for applications
     * @param applicationClosingDate End date for applications
     * @param hdbManager Managing HDB officer
     * @param availableOfficerSlots Number of officer slots available
     * @param applicants List of applicants
     * @param visiblity Visibility of the project
     * @param hdbOfficers List of assigned officers
     * @param enquiries List of project enquiries
     * @param maritalStatus Marital status restriction
     */

    public Project(String projectName, String neighborhood, ArrayList<FlatType> flatTypes, Date applicationOpeningDate, Date applicationClosingDate,
    		HDBManager hdbManager, int availableOfficerSlots, boolean visiblity, ArrayList<Applicant> applicants, ArrayList<HDBOfficer> hdbOfficers, ArrayList<Enquiry> enquiries,
    		ArrayList<MaritalStatus> allowedGroups) {
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
    	this.allowedGroups = allowedGroups;  	
    }
    /**
     * Sets the project name.
     * @param projectName The new project name
     */
    //set methods
    public void setProjectName(String projectName){
        this.projectName = projectName;
    }
    /**
     * Sets the neighborhood name.
     * @param neighborhood The new neighborhood
     */
    public void setNeiborhood(String neighborhood){
        this.neighborhood = neighborhood;
    }

    /**
     * Sets the application opening date.
     * @param applicationOpeningDate The new opening date
     */
    public void setApplicationOpeningDate(Date applicationOpeningDate){
        this.applicationOpeningDate = applicationOpeningDate;
    }
    /**
     * Sets the application closing date.
     * @param applicationClosingDate The new closing date
     */
    public void setApplicationClosingDate(Date applicationClosingDate){
        this.applicationClosingDate = applicationClosingDate;
    }

    /**
     * Sets the hdb manager of the project.
     * @param hdbManager The new manager
     */
    public void setHDBManager(HDBManager hdbManager){
        this.hdbManager = hdbManager;
    }
    /**
     * Sets the hdb officers of current project.
     * @param hdbOfficer The new hdbOfficers
     */
    public void setHDBOfficer(ArrayList<HDBOfficer> hdbOfficer){
        this.hdbOfficers = hdbOfficer;
    }

    /**
     * Sets the available officer slots to be assigned.
     * @param availableOfficerSlots The new available officer slots.
     */
    public void setAvailableOfficerSlots(int availableOfficerSlots){
        this.availableOfficerSlots = availableOfficerSlots;
    }
    /**
     * Sets the available flat types.
     * @param flatType The new availabe flate type list.
     */
    public void setFlatType(ArrayList<FlatType> flatType){
        this.flatTypes = flatType;
    }
    /**
     * Sets the visibility of the project
     * @param visibility The new visibility condition.
     */
    public void setVisibility(boolean visibility) {
    	this.visibility = visibility;
    }
    /**
     * Sets the marital status of the project
     * @param set MaritalStatus condition
     */
    public void setAllowedGroups(ArrayList<MaritalStatus> allowedGroups){
    	this.allowedGroups = allowedGroups;
    }    

    //get methods
    /**
     * Gets the project name.
     * @return Current project name
     */
    public String getProjectName(){
        return this.projectName;
    }
    /**
     * Gets the neighborhood name.
     * @return Current neighborhood
     */
    public String getNeiborhood(){
        return this.neighborhood;
    }
    /**
     * Gets the application opening date.
     * @return Current application opening date
     */
    public Date getApplicationOpeningDate(){
        return this.applicationOpeningDate;
    }
    /**
     * Gets the application closing date.
     * @return Current application closing date
     */
    public Date getApplicationClosingDate(){
        return this.applicationClosingDate;
    }
    /**
     * Gets the assigned project manager.
     * @return Current hdb manager
     */
    public HDBManager getHDBManager(){
        return this.hdbManager;
    }
    /**
     * Gets the assigned project officers.
     * @return Current hdb officers
     */
    public ArrayList<HDBOfficer> getHDBOfficer(){
        return this.hdbOfficers;
    }
    /**
    * Gets the available officer slots.
    * @return Current available officer slots
    */
    public int getAvailableOfficerSlots(){
        return this.availableOfficerSlots;
    }
    /**
    * Gets the visibility of the project.
    * @return Current project visibility
    */
    public boolean getVisibility(){
        return visibility;
    }
    /**
    * Gets the allowed marital status.
    * @return Current available marital status
    */
    public ArrayList<MaritalStatus> getAllowedGroups() {
    	return allowedGroups;
    }
    /**
    * Gets the available flat types
    * @return Current available flat types
    */
    public ArrayList<FlatType> getFlatTypes(){ return flatTypes; }
    
    /**
     * @param applicant new applicant to be added
    * Add new applicant
    */
    public void addApplicant(Applicant applicant){

    }
    /**
     * @param applicant the applicant to be removed
    * Remove project applicant
    */
    public void removeApplicant(Applicant applicant){

    }
    /**
     * @param hdbOfficer New hdb officer to be added into the project
    * Assign hdb officer
    */
    public void addHDBOfficer(HDBOfficer hdbOfficer) {
    	hdbOfficers.add(hdbOfficer);
    }
    /**
     * @param hdbOfficer the targeted officer to be removed
    * Remove hdb officer
    */
    public void removeHDBOfficer(HDBOfficer hdbOfficer){

    }
    /**
     * @param enquiry the enquiry to be added into project
    * Add enquiry to project
    */
    public void addEnquiry(Enquiry enquiry){

    }
    /**
     * @param flatType flate type variable to be modified
    * Reduce amout of units respective to flat type
    */
    public void decreaseRemainingUnits(FlatType flatType){

    }
    /**
     * Generates string representation of the project information.
     * @return Formatted project details including:
     *         - Project name and neighborhood
     *         - Application period
     *         - Flat type details
     */
    public String toString() {
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
    	String formattedOpeningDate = formatter.format(applicationOpeningDate);
    	String formattedClosingDate = formatter.format(applicationClosingDate);
    	String flatDetails = FlatTypeLogic.displayFlatTypes(flatTypes);
		String projectDetails = String.format("%s, %s, %s to %s\n%s", projectName, neighborhood, formattedOpeningDate, formattedClosingDate, flatDetails);
		return projectDetails;
    }
    /**
     * Generates a storage-optimized string representation of the project.
     * @return All project details in a format suitable for file storage
     */
    public String toStore() {
    	SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
        String projectDetails = "";
        projectDetails = projectDetails + this.projectName + ",";
        projectDetails = projectDetails + this.neighborhood + ",";

        for(FlatType flatType : this.flatTypes){
            if(flatType instanceof TwoRoom){
                projectDetails = projectDetails + "2-Room,";
                projectDetails = projectDetails + String.valueOf(flatType.getUnits()) + ",";
                projectDetails = projectDetails + String.valueOf(flatType.getPrice()) + ",";
            }else if(flatType instanceof ThreeRoom){
                projectDetails = projectDetails + "3-Room,";
                projectDetails = projectDetails + String.valueOf(flatType.getUnits()) + ",";
                projectDetails = projectDetails + String.valueOf(flatType.getPrice()) + ",";
            }
        }
    	projectDetails = projectDetails + formatter.format(this.applicationOpeningDate) + ",";
    	projectDetails = projectDetails + formatter.format(this.applicationClosingDate) + ",";
        projectDetails = projectDetails + this.hdbManager.getNRIC() + ",";

        projectDetails = projectDetails + String.valueOf(this.availableOfficerSlots) + ",";
        if(this.hdbOfficers.get(0) != null){
            int i = 0;
            for(HDBOfficer hdbOfficer : this.hdbOfficers){
                if(i++ == hdbOfficers.size() - 1){
                    projectDetails = projectDetails + hdbOfficer.getNRIC() + ",";
                    break;
                }
                projectDetails = projectDetails + hdbOfficer.getNRIC() + "&";
            }
        }else{
            projectDetails = projectDetails + " ,";
        }

        if(visibility){
            projectDetails = projectDetails + "true"; 
        }else{
            projectDetails = projectDetails + "false"; 
        }
		return projectDetails;
    }

    /**
     * @param application new application object to be added
     * Add application to project
    */
    public void addApplication(Application application){
        applications.add(application);
    }
    /**
    * Get all project applications
    * @return List of applications
    */
    public ArrayList<Application> getApplications(){
        return applications;
    }
    
    public ArrayList<Enquiry> getEnquiries(){
        return enquiries;
    }

}