import java.util.ArrayList;

/**
 * FlatTypeLogic.java
 * 
 * This class provides utility methods for managing flat types in a housing project.
 * It includes methods for displaying flat types, filtering by marital status, and updating available units.
 * 
 * @see FlatType
 * @see MaritalStatus
 */
public class FlatTypeLogic {

    /**
     * Generates a formatted string displaying all flat types and their details.
     * 
     * @param flatTypes the list of flat types to display
     * @return formatted string containing flat type details
     */
    public static String displayFlatTypes(ArrayList<FlatType> flatTypes) {
        StringBuilder flatDetails = new StringBuilder();
        for (FlatType flatType : flatTypes) {
            flatDetails.append(String.format("Flat Type: %s, Units: %d, Price: $%.2f\n",
                    flatType.getClass().getSimpleName(), flatType.getUnits(), flatType.getPrice()));
        }
        return flatDetails.toString();
    }

    /**
     * Generates a numbered list of flat types for selection purposes.
     * 
     * @param flatTypes the list of flat types to display
     * @return formatted string with numbered flat type options
     */
    public static String displayFlatTypesView(ArrayList<FlatType> flatTypes) {
        StringBuilder flatDetails = new StringBuilder();
        for (int i = 0; i < flatTypes.size(); i++) {
            FlatType flatType = flatTypes.get(i);
            flatDetails.append(String.format("%d. Flat Type: %s, Units: %d, Price: $%.2f\n",
                    (i+1), flatType.getClass().getSimpleName(), flatType.getUnits(), flatType.getPrice()));
        }
        return flatDetails.toString();
    }
    
    /**
     * Retrieves the available units for a specific flat type.
     * 
     * @param flatTypes the list of flat types to search
     * @param type the flat type to check
     * @return number of available units (0 if not found)
     */
    public static int returnFilteredFlatTypeUnits(ArrayList<FlatType> flatTypes, FlatType type) {
        for (FlatType flat : flatTypes) {
            if (flat.equals(type)) {
                return flat.getUnits();
            }
        }
        return 0;
    }
    
    /**
     * Decrements the available units for a specific flat type (e.g., when booked).
     * 
     * @param flatTypes the list of flat types to update
     * @param type the flat type to modify
     */
    public static void updateFilteredFlatTypeUnits(ArrayList<FlatType> flatTypes, FlatType type) {
        for (FlatType flat : flatTypes) {
            if (flat.equals(type)) {
                flat.setUnits(flat.getUnits()-1);
            }
        }
    }
    
    /**
     * Increments the available units for a specific flat type (e.g., when cancelled).
     * 
     * @param flatTypes the list of flat types to update
     * @param type the flat type to modify
     */
    public static void updateIncreaseFilteredFlatTypeUnits(ArrayList<FlatType> flatTypes, FlatType type) {
        for (FlatType flat : flatTypes) {
            if (flat.equals(type)) {
                flat.setUnits(flat.getUnits()+1);
            }
        }
    }
    
    /**
     * Filters flat types based on marital status eligibility.
     * <p>
     * Returns flat types that open to the specified
     * marital status in their allowed groups.
     * </p>
     *
     * @param FlatTypeList List of flat types to filter
     * @param maritalStatus The marital status to check for eligibility
     * @return Filtered list of compatible flat types
     *
     * @see FlatType
     * @see MaritalStatus
     */
    public static ArrayList<FlatType> filterFlatTypesByMaritalStatus(ArrayList<FlatType> FlatTypeList, MaritalStatus maritalStatus) {
        ArrayList<FlatType> filteredFlatTypes = new ArrayList<>();

        for (FlatType flatType : FlatTypeList) {
            // If the flat type's allowed groups contain the applicant's marital status
            if (flatType.getAllowedGroups().contains(maritalStatus)) {
                filteredFlatTypes.add(flatType);
            }
        }
        return filteredFlatTypes;
    }   
    
    
    
}