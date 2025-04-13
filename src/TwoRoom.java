import java.util.ArrayList;

public class TwoRoom extends FlatType{
    public TwoRoom(int units, double price, MaritalStatus allowedGroup) {
        super(units, price, allowedGroup, "2-Room");
        // Use '2-Room' instead of 'TwoRoom' to sync .txt
    }
    
    public TwoRoom(int units, double price, ArrayList<MaritalStatus> allowedGroups) {
    	super(units, price, allowedGroups, "2-Room");
    	// Use '2-Room' instead of 'TwoRoom' to sync .txt
    }
}
