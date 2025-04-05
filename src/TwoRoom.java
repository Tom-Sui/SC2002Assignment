import java.util.ArrayList;

public class TwoRoom extends FlatType{
    public TwoRoom(int units, double price, MaritalStatus allowedGroup) {
        super(units, price, allowedGroup);
    }
    
    public TwoRoom(int units, double price, ArrayList<MaritalStatus> allowedGroups) {
    	super(units, price, allowedGroups);
    }
}
