import java.util.ArrayList;

public abstract class FlatType {
	private int units;
	private double price;
	private ArrayList<MaritalStatus> allowedGroups = new ArrayList<MaritalStatus>();
	private String flatTypeName;
	
	public FlatType() {};
	
	public FlatType(int units, double price, MaritalStatus allowedGroup, String flatTypeName) {
		this.units = units;
		this.price = price;
		this.allowedGroups.add(allowedGroup);
		this.flatTypeName = flatTypeName;
	}
	
	public FlatType(int units, double price, ArrayList<MaritalStatus> allowedGroups, String flatTypeName) {
		this.units = units;
		this.price = price;
		this.allowedGroups = allowedGroups;
		this.flatTypeName = flatTypeName;
	}
	
	public void setUnits(int units) { this.units = units; }
	public void setPrice(double price) { this.price = price; }
	public int getUnits() { return units; }
	public double getPrice() { return price; }
	public String getFlatTypeName() { return flatTypeName;}
	public ArrayList<MaritalStatus> getAllowedGroups() { return allowedGroups; }
	
}
