public class FlateType {
    private String flateType;
    private int flateCounts;
    private int flatePrice;

    public FlateType(){
        this("NULL",0,0);
    }

    public FlateType(String flateType, int flateCounts, int flatePrice){
        this.flateType = flateType;
        this.flateCounts = flateCounts;
        this.flatePrice = flatePrice;
    }
    //set method
    public void setFlateType(String flateType){
        this.flateType = flateType;
    }
    public void setFlateCounts(int flateCounts){
        this.flateCounts = flateCounts;
    }
    public void setFlatePrice(int flatePrice){
        this.flatePrice = flatePrice;
    }

    //get method
    public String getFlateType(){
        return this.flateType;
    }
    public int getFlateCounts(){
        return this.flateCounts;
    }
    public int getFlatePrice(){
        return this.flatePrice;
    }
}
