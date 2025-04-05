import java.util.ArrayList;

public class ThreeRoom extends FlatType {
    public ThreeRoom(int units, double price, MaritalStatus allowedGroup) {
        super(units, price, allowedGroup);
    }
    
    public ThreeRoom(int units, double price, ArrayList<MaritalStatus> allowedGroups) {
    	super(units, price, allowedGroups);
    }
}
