import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
            	Project filteredProject = new Project(project);

                // Filter the flat types
                ArrayList<FlatType> filteredFlats = new ArrayList<>();
                for (FlatType flatType : project.getFlatTypes()) {
                    if (flatType.getAllowedGroups().contains(maritalStatus) || flatType.getAllowedGroups().isEmpty()) {
                        filteredFlats.add(flatType);
                    }
                }

                // Update the flat types of the filtered project
                filteredProject.setFlatType(filteredFlats);
                filteredProjects.add(filteredProject);
            }
        }
        return filteredProjects;
    }
    
    public static ArrayList<Project> filterProjectsForOfficer(ArrayList<Project> projects, HDBOfficer officer) {
        ArrayList<Project> filteredProjects = new ArrayList<>();
        
        for (Project project : projects) {
            // Officers can only apply to projects that they are not in charge of.
            if (!project.getHDBOfficer().contains(officer)) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }


    
    /**
     * Displays all available projects that are visible to applicants.
     * 
     * @param projects list of all projects to display
     */
    public static int viewAvailableProjects(ArrayList<Project> projects, Applicant applicant) {
        List<Application> pastApplications = applicant.getPastApplications();
        List<Project> pastAppliedProjects = pastApplications.stream()
        	    .map(Application::getProject)  // extract the project from each application
        	    .collect(Collectors.toCollection(ArrayList::new));
        
        System.out.println("Past applied projects:");
        for (Project project : pastAppliedProjects) {
            System.out.println(project.toString());
        }
        
        System.out.println("Newly available projects:");
        int idx = 0;
        
        for (Project newProject : projects) {
        	 System.out.println("Project ID: " + (idx+1));
             System.out.println(newProject.toString());
             idx++;
        }
        
        return idx;
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


        

}

