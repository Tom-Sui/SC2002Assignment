/**
 * Represents the possible registration states for an HDB officer's project assignment.
 * <p>
 * This enum defines the various states for an officer's request to be assigned to a project.
 * </p>
 *
 */
public enum OfficerRegistrationStatus {
    /**
     * The officer's registration request is awaiting approval.
     * <p>
     * This is the initial state when an officer first registers for a project.
     * </p>
     * 
     */
    PENDING,
    /** 
     * The officer's registration has been approved by the manager.
     * <p>
     * In this state, the officer is officially assigned to the project
     * and can perform managing officer duties.
     * </p>
     * 
     *
    */
    APPROVED,
    /**
     * The officer's registration has been rejected by the manager.
     * <p>
     * The officer will not be assigned to the requested project.
     * </p>
     */
    REJECTED
}