import java.util.ArrayList;

public class ProjectLogic {
    // Method to filter projects based on the marital status of the applicant
    public static ArrayList<Project> filterProjectsByMaritalStatus(ArrayList<Project> projects, MaritalStatus maritalStatus) {
        ArrayList<Project> filteredProjects = new ArrayList<>();

        for (Project project : projects) {
            // If the project's marital status matches the applicant's marital status
            if (project.getMaritalStatus() == maritalStatus || project.getMaritalStatus() == null) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }

    public static ArrayList<FlatType> filterFlatTypesByMaritalStatus(ArrayList<FlatType> FlatTypeList, MaritalStatus maritalStatus) {
        ArrayList<FlatType> filteredFlatTypes = new ArrayList<>();

        for (FlatType flatType : FlatTypeList) {
            // If the flat type's allowed groups contain the applicant's marital status
            // or if the flat type's allowed groups is null
            if (flatType.getAllowedGroups().contains(maritalStatus)) {
                filteredFlatTypes.add(flatType);
            }
        }
        return filteredFlatTypes;
    }   
}

