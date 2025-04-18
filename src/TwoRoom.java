import java.util.ArrayList;

/**
 * Represents a 2-Room flat type in the HDB application system.
 * Extends the base {@link FlatType} class with specific configuration for 2-Room flats.
 * 
 * <p>This class provides constructors to initialize a 2-Room flat with:
 * <ul>
 *   <li>Number of available units</li>
 *   <li>Price of the flat</li>
 *   <li>Allowed marital status groups (single or both single and married)</li>
 * </ul>
 * 
 * <p>The type is automatically set to "2-Room" to maintain consistency with data files.
 */
public class TwoRoom extends FlatType {
    
    /**
     * Constructs a TwoRoom flat with specified units, price, and single allowed marital status.
     * 
     * @param units the number of available units for this flat type (must be positive)
     * @param price the price per unit (must be positive)
     * @param allowedGroup the marital status allowed to apply (typically SINGLE)
     */
    public TwoRoom(int units, double price, MaritalStatus allowedGroup) {
        super(units, price, allowedGroup, "2-Room");
    }
    
    /**
     * Constructs a TwoRoom flat with specified units, price, and multiple allowed marital statuses.
     * 
     * @param units the number of available units for this flat type (must be positive)
     * @param price the price per unit (must be positive)
     * @param allowedGroups list of permitted marital statuses (typically SINGLE and MARRIED)
     */
    public TwoRoom(int units, double price, ArrayList<MaritalStatus> allowedGroups) {
        super(units, price, allowedGroups, "2-Room");
    }
}