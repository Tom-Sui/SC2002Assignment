import java.util.ArrayList;

public class ThreeRoom extends FlatType {
    public ThreeRoom(int units, double price, MaritalStatus allowedGroup) {
        super(units, price, allowedGroup, "3-Room");
        // Use '3-Room' instead of 'ThreeRoom' to sync .txt
    }
    
    public ThreeRoom(int units, double price, ArrayList<MaritalStatus> allowedGroups) {
    	super(units, price, allowedGroups, "3-Room");
    	// Use '3-Room' instead of 'ThreeRoom' to sync .txt
    }
}
