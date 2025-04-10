import java.util.ArrayList;

public class FlatTypeLogic {

//    // Method to filter flat types based on marital status
//    public static ArrayList<FlatType> filterByMaritalStatus(ArrayList<FlatType> flatTypes, MaritalStatus maritalStatus) {
//        ArrayList<FlatType> filteredFlatTypes = new ArrayList<>();
//        for (FlatType flatType : flatTypes) {
//            if (flatType.getAllowedGroups().contains(maritalStatus)) {
//                filteredFlatTypes.add(flatType);
//            }
//        }
//        return filteredFlatTypes;
//    }
    // Method to display flat types (could be used by Applicant to display available flats)
    public static String displayFlatTypes(ArrayList<FlatType> flatTypes) {
        StringBuilder flatDetails = new StringBuilder();
        for (FlatType flatType : flatTypes) {
            flatDetails.append(String.format("Flat Type: %s, Units: %d, Price: $%.2f\n",
                    flatType.getClass().getSimpleName(), flatType.getUnits(), flatType.getPrice()));
        }
        return flatDetails.toString();
    }

    // Method to display flat types (could be used by Applicant to display available flats for application)
    public static String displayFlatTypesView(ArrayList<FlatType> flatTypes) {
        StringBuilder flatDetails = new StringBuilder();
        for (int i = 0; i < flatTypes.size(); i++) {
            FlatType flatType = flatTypes.get(i);
            flatDetails.append(String.format("%d Flat Type: %s, Units: %d, Price: $%.2f\n",
                    (i+1), flatType.getClass().getSimpleName(), flatType.getUnits(), flatType.getPrice()));
        }
        return flatDetails.toString();
    }
    
    // might need to check if flat exist
    public static int returnFilteredFlatTypeUnits(ArrayList<FlatType> flatTypes, FlatType type) {
    	for (FlatType flat : flatTypes) {
    		if (flat.equals(type)) {
    			return flat.getUnits();
    		}
    	}
    	return 0;
    }
    
    public static void updateFilteredFlatTypeUnits(ArrayList<FlatType> flatTypes, FlatType type) {
    	for (FlatType flat : flatTypes) {
    		if (flat.equals(type)) {
    			flat.setUnits(flat.getUnits()-1);
    		}
    	}
    }

}
