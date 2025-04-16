import java.util.ArrayList;
/**
 * Provides logic operations on Project objects.
 * <p>
 * Utility class that contains static methods for filtering and displaying
 * project data based on various criteria including marital status and
 * officer assignments.
 * </p>
 * 
 */
public class ProjectLogic {
    /**
     * Method to filter projects based on the marital status of the applicant
     * Returns projects that either:
     * <ul>
     *   <li>Have no marital status restriction (null)</li>
     *   <li>Match the specified marital status</li>
     * </ul>
     *
     * @param projects List of projects to filter
     * @param maritalStatus The marital status to filter by
     * @return Filtered list of compatible projects
     *
     * @see Project
     * @see MaritalStatus
     */
    public static ArrayList<Project> filterProjectsByMaritalStatus(ArrayList<Project> projects, MaritalStatus maritalStatus) {
        ArrayList<Project> filteredProjects = new ArrayList<>();
        
        for (Project project : projects) {
            // If the project's marital status matches the applicant's marital status
            if (project.getAllowedGroups().contains(maritalStatus) || project.getAllowedGroups().isEmpty()) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }
    /**
     * Displays HDB officers details.
     * <p>
     * Prints each officer's details with a numbered index,
     * using their toString() representation.
     * 
     * The display format is:
     * Officer ID: [index]
     * [officer details]
     * </p>
     *
     * @param officers List of HDB officers to display
     * 

     *
     * @see HDBOfficer#toString()
     */
    public static void displayHDBOfficers(ArrayList<HDBOfficer> officers) {
        for (int i = 0; i < officers.size(); i++) {
            HDBOfficer officer = officers.get(i);
            System.out.println("Officer ID: " + (i+1));  // index is i
            System.out.println(officer.toString());
        }
        System.out.println();
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

