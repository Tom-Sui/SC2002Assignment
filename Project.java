import java.sql.Date;

public class Project {
    private String projectName;
    private String neiborhood;
    private int twoRoomUnits;
    private int threeRoomUnits;
    private Date applicationOpeningData;
    private Date applicationClosingData;
    private HDBManager hdbManager;
    private int availableOfficerSlots;
    private boolean visibility;
    private Applicant[] applicants;
    private HDBOfficer[] hdbOfficers;
    private Enquiry[] enquiry;

    //Wasn't stated in the UML diagram
    private FlateType flateType;


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
    public FlateType getRemainingUnits(FlateType flateType){
        return this.flateType;
    }
    public void decreaseRemainingUnits(FlateType flateType){

    }

    

}
