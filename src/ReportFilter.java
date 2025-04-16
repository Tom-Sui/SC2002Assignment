import org.w3c.dom.ranges.Range;

/**
 * A class representing filter criteria for generating reports on HDB applications.
 * Allows filtering by flat type, marital status, age range, and project name.
 * 
 * <p>This class is typically used to:
 * <ul>
 *   <li>Filter applicant lists for reporting purposes</li>
 *   <li>Generate statistics on application patterns</li>
 *   <li>Create targeted reports for specific demographic groups</li>
 * </ul>
 */
public class ReportFilter {
    private FlatType flatType;
    private MaritalStatus maritalStatus;
    private Range ageRange;
    private String projectName;

    // /**
    //  * Gets the flat type filter criteria.
    //  * @return the FlatType to filter by, or null if not filtered by flat type
    //  */
    // public FlatType getFlatType() {
    //     return flatType;
    // }

    // /**
    //  * Sets the flat type filter criteria.
    //  * @param flatType the FlatType to filter by (null to remove this filter)
    //  */
    // public void setFlatType(FlatType flatType) {
    //     this.flatType = flatType;
    // }

    // /**
    //  * Gets the marital status filter criteria.
    //  * @return the MaritalStatus to filter by, or null if not filtered by marital status
    //  */
    // public MaritalStatus getMaritalStatus() {
    //     return maritalStatus;
    // }

    // /**
    //  * Sets the marital status filter criteria.
    //  * @param maritalStatus the MaritalStatus to filter by (null to remove this filter)
    //  */
    // public void setMaritalStatus(MaritalStatus maritalStatus) {
    //     this.maritalStatus = maritalStatus;
    // }

    // /**
    //  * Gets the age range filter criteria.
    //  * @return the Range object representing age boundaries, or null if not filtered by age
    //  */
    // public Range getAgeRange() {
    //     return ageRange;
    // }

    // /**
    //  * Sets the age range filter criteria.
    //  * @param ageRange the Range object representing age boundaries (null to remove this filter)
    //  */
    // public void setAgeRange(Range ageRange) {
    //     this.ageRange = ageRange;
    // }

    // /**
    //  * Gets the project name filter criteria.
    //  * @return the project name to filter by, or null if not filtered by project
    //  */
    // public String getProjectName() {
    //     return projectName;
    // }

    // /**
    //  * Sets the project name filter criteria.
    //  * @param projectName the name of the project to filter by (null to remove this filter)
    //  */
    // public void setProjectName(String projectName) {
    //     this.projectName = projectName;
    // }
}