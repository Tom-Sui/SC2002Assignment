import java.util.ArrayList;

/**
 * An abstract base class representing flat type.
 * Defines common properties and behaviors for all flat types (2-Room, 3-Room, etc.).
 * 
 * <p>This class maintains information about:
 * <ul>
 *   <li>Available units of this flat type</li>
 *   <li>Price per unit</li>
 *   <li>Allowed marital status groups that can apply</li>
 *   <li>Flat type name/classification</li>
 * </ul>
 * 
 * <p>Concrete flat type classes should extend this base class.
 */
public abstract class FlatType {
    private int units;
    private double price;
    private ArrayList<MaritalStatus> allowedGroups = new ArrayList<MaritalStatus>();
    
    /**
     * Default constructor (available for subclasses).
    */
    public FlatType() {};
    
    /**
     * Constructs a FlatType with single allowed marital status group.
     * 
     * @param units the number of available units (must be positive)
     * @param price the price per unit (must be positive)
     * @param allowedGroup the marital status allowed to apply
     */
    public FlatType(int units, double price, MaritalStatus allowedGroup) {
        this.units = units;
        this.price = price;
        this.allowedGroups.add(allowedGroup);
    }
    
    /**
     * Constructs a FlatType with multiple allowed marital status groups.
     * 
     * @param units the number of available units (must be positive)
     * @param price the price per unit (must be positive)
     * @param allowedGroups list of permitted marital statuses
     */
    public FlatType(int units, double price, ArrayList<MaritalStatus> allowedGroups) {
        this.units = units;
        this.price = price;
        this.allowedGroups = allowedGroups;
    }
    
    /**
     * Sets the number of available units.
     * @param units the new number of units (must be positive)
     */
    public void setUnits(int units) { this.units = units; }
    
    /**
     * Sets the price per unit.
     * @param price the new price (must be positive)
     */
    public void setPrice(double price) { this.price = price; }
    
    /**
     * Sets the list of allowed marital status groups.
     * @param allowedGroups list of permitted marital statuses
     */
    public void setAllowedGroups(ArrayList<MaritalStatus> allowedGroups){ this.allowedGroups = allowedGroups; }
    
    /**
     * Gets the number of available units.
     * @return current number of units
     */
    public int getUnits() { return units; }
    
    /**
     * Gets the price per unit.
     * @return current price
     */
    public double getPrice() { return price; }    
    
    /**
     * Gets the list of allowed marital status groups.
     * @return ArrayList of permitted marital statuses
     */
    public ArrayList<MaritalStatus> getAllowedGroups() { return allowedGroups; }

    /**
     * Gets the flat type name.
     * @return the flat type name
     */
    public abstract String getFlatTypeName();
    /**
     * Gets the flat type name in a flat format (e.g., "2-Room", "3-Room").
     * @param name the flat type name to check against
     * @return the flat type name in a flat format
     */
    public boolean matchesTypeName(String name) {
        return getFlatTypeName().equalsIgnoreCase(name);
    }

}