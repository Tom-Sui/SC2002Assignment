import java.util.ArrayList;

/**
 * Represents a 3-Room flat type in the HDB application system.
 * Extends the base {@link FlatType} class with specific configuration for 3-Room flats.
 * 
 * <p>This class provides constructors to initialize a 3-Room flat with:
 * <ul>
 *   <li>Number of available units</li>
 *   <li>Price of the flat</li>
 *   <li>Allowed marital status groups (single or both single and married)</li>
 * </ul>
 * 
 * <p>The type is automatically set to "3-Room" to maintain consistency with data file formats.
 */
public class ThreeRoom extends FlatType {
    
    /**
     * Constructs a ThreeRoom flat with specified units, price, and single marital status restriction.
     * 
     * @param units the number of available units for this flat type (must be positive)
     * @param price the price per unit (must be positive)
     * @param allowedGroup the marital status allowed to apply (typically both SINGLE and MARRIED)
     */
    public ThreeRoom(int units, double price, MaritalStatus allowedGroup) {
        super(units, price, allowedGroup);
    }
    
    /**
     * Constructs a ThreeRoom flat with specified units, price, and multiple allowed marital statuses.
     * 
     * @param units the number of available units for this flat type (must be positive)
     * @param price the price per unit (must be positive)
     * @param allowedGroups list of permitted marital statuses (typically contains both SINGLE and MARRIED)
     */
    public ThreeRoom(int units, double price, ArrayList<MaritalStatus> allowedGroups) {
        super(units, price, allowedGroups);
    }

	public String getFlatTypeName() {
		return "3-Room";
	}
}