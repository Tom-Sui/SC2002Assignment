import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Project {
    private String projectName;
    private String neiborhood;
    // private int twoRoomUnits;
    // private int threeRoomUnits;
    private LocalDate applicationOpeningData;
    private LocalDate applicationClosingData;
    private HDBManager hdbManager;
    private HDBOfficer[] hdbOfficer;
    private int availableOfficerSlots;
    private boolean visibility;
    private Applicant[] applicants;

    private Enquiry[] enquiry;
    private MaritialStatus maritialStauts;

    //Wasn't stated in the UML diagram
    private FlateType[] flateType;

    //set methods
    public void setProjectName(String projectName){
        this.projectName = projectName;
    }
    public void setNeiborhood(String neiborhood){
        this.neiborhood = neiborhood;
    }
    public void setFlateType(FlateType[] flateType){
        this.flateType = flateType;
    }
    public void setApplicationOpeningData(LocalDate applicationOpeningData){
        this.applicationOpeningData = applicationOpeningData;
    }
    public void setApplicationClosingData(LocalDate applicationClosingData){
        this.applicationClosingData = applicationClosingData;
    }
    public void setHDBManager(HDBManager hdbManager){
        this.hdbManager = hdbManager;
    }
    public void setHDBOfficer(HDBOfficer[] hdbOfficer){
        this.hdbOfficer = hdbOfficer;
    }
    public void setAvailableOfficerSlots(int availableOfficerSlots){
        this.availableOfficerSlots = availableOfficerSlots;
    }

    //get methods
    public String getProjectName(){
        return this.projectName;
    }
    public String getNeiborhood(){
        return this.neiborhood;
    }
    public FlateType[] setUnitType(){
        return this.flateType;
    }
    public LocalDate getApplicationOpeningData(){

        return this.applicationOpeningData;
    }
    public LocalDate getApplicationClosingData(){

        return this.applicationClosingData;
    }
    public HDBManager getHDBManager(){
        return this.hdbManager;
    }
    public HDBOfficer[] getHDBOfficer(){
        return this.hdbOfficer;
    }
    public int getAvailableOfficerSlots(){
        return this.availableOfficerSlots;
    }

    //Debug method
    public void debugOut(){
        System.out.println(this.projectName);
        System.out.println(this.neiborhood);
        System.out.println(this.flateType[0].getFlateType());
        System.out.println(this.applicationOpeningData);
        System.out.println(this.applicationClosingData);
        System.out.println(this.hdbManager.getName());
        System.out.println(this.hdbOfficer[0].getName());
    }
    //Methods
    public boolean isApplicationOpen(){
        return true;
    }
    public void addApplicant(Applicant applicant){

    }
    public void removeApplicant(Applicant applicant){

    }
    public void removeHDBOfficer(HDBOfficer hdbOfficer){

    }
    public void addEnquiry(Enquiry enquiry){

    }
    public FlateType[] getRemainingUnits(FlateType[] flateType){
        return this.flateType;
    }
    public void decreaseRemainingUnits(FlateType flateType){

    }
}
