import java.util.ArrayList;

public abstract class FlatType {
	private int units;
	private double price;
	private ArrayList<MaritalStatus> allowedGroups = new ArrayList<MaritalStatus>();
	
	
	public FlatType() {};
	
	public FlatType(int units, double price, MaritalStatus allowedGroup) {
		this.units = units;
		this.price = price;
		this.allowedGroups.add(allowedGroup);
	}
	
	public FlatType(int units, double price, ArrayList<MaritalStatus> allowedGroups) {
		this.units = units;
		this.price = price;
		this.allowedGroups = allowedGroups;
	}
	
	public void setUnits(int units) { this.units = units; }
	public void setPrice(double price) { this.price = price; }
	public int getUnits() { return units; }
	public double getPrice() { return price; }
	public ArrayList<MaritalStatus> getAllowedGroups() { return allowedGroups; }
	
}
