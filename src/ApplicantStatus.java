/**
 * Represents the statuses of an applicant's housing application.
 * <p>
 * This enum defines the various stages an application can be in during
 * the housing allocation process.
 * </p>
 * 
 * @see Application
 */
public enum ApplicantStatus {
    /**
     * The application is under review and no decision has been made yet.
     */
    PENDING,
    /**
     * The application has been approved and the applicant is eligible for a flat.
     */
    SUCCESSFUL,
    /**
     * The application has been rejected.
     */
    UNSUCCESSFUL,
    /**
     * The applicant has successfully booked a flat.
     */
    BOOKED
}