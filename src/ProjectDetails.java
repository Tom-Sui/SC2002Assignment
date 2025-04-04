import java.sql.Date;

public class ProjectDetails {
    private String projectName;
    private String neighborhood;
    private int twoRoomUnits;
    private int threeRoomUnits;
    private Date applicationOpeningDate;
    private Date applicationClosingDate;
    private int availableOfficerSlots;

    //set method
    public void setProjectName(String projectName){
        this.projectName = projectName;
    }
    public void setNeighborhood(String neighborhood){
        this.neighborhood = neighborhood;
    }
    public void setTwoRoomUnits(int twoRoomUnits){
        this.twoRoomUnits = twoRoomUnits;
    }
    public void setThreeRoomUnits(int threeRoomUnits){
        this.threeRoomUnits = threeRoomUnits;
    }
    public void setApplicationOpeningDate(Date applicationOpeningDate){
        this.applicationOpeningDate = applicationOpeningDate;
    }
    public void applicationClosingDate(Date applicationClosingDate){
        this.applicationClosingDate = applicationClosingDate;
    }
    public void setAvailableOfficerSlots(int availableOfficerSlots){
        this.availableOfficerSlots = availableOfficerSlots;
    }
    //get method
    public String getProjectName(){
        return this.projectName;
    }
    public String getNeighborhood(){
        return this.neighborhood;
    }
    public int getTwoRoomUnits(){
        return this.twoRoomUnits;
    }
    public int getThreeRoomUnits(){
        return this.threeRoomUnits;
    }
    public Date getApplicationOpeningDate(){
        return this.applicationOpeningDate;
    }
    public Date getApplicationClosingDate(){
        return this.applicationClosingDate;
    }
    public int getAvailableOfficerSlots(){
        return this.availableOfficerSlots;
    }
}
