/**
 * Represents the possible statuses of a housing application.
 * <p>
 * This enum defines all valid states an application can be in
 * Each status represents a specific phase in the application
 * review and approval process.
 * </p>
 * 
 * @see Application
 */
public enum ApplicationStatus {
    /**
     * The application has been submitted but not yet processed.
     * This is the initial state for all new applications.
     */
    PENDING,
    /**
     * The applicant has requested to withdraw the application, but the request
     * is pending approval by the housing authority.
     */
    PENDINGWITHDRAWAL,
     /** 
     * The application has been approved and the applicant is eligible for housing
     */ 
    SUCCESSFUL,
    /** 
     * The application has been rejected.
     */
    UNSUCCESSFUL,
    /**
     * The applicant has successfully booked a flat through this application.
     * This is the final successful state.
     */
    BOOKED,
     /**
     * The application has been officially withdrawn, either by applicant request
     * or administrative action. This is the final unsuccessful state.
     */
    WITHDRAWN
}