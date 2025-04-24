/**
 * The Booking class represents a booking made by an officer for a specific application.
 * It contains information about the booking ID, application ID, and the officer's NRIC.
 * 
 */
public class Booking {
	private static int bookingId = 1;
	private int applicationId;
	private String officerNRIC;
	/**
	 * Default constructor for Booking class.
	 * <p>
	 * This constructor initializes the booking ID and increments the static booking ID counter.
	 * </p>
	 */
	public Booking() {
		Booking.bookingId++;
	};
	/**
	 * Constructor for Booking class.
	 * 
	 * @param applicationId The application ID for the booking
	 * @param officerNRIC The NRIC of the officer who is managing the booking
	 */
	public Booking(int applicationId, String officerNRIC) {
		this.applicationId = applicationId;
		this.officerNRIC = officerNRIC;
	}
	/**
	 * Gets the booking ID.
	 * 
	 * @return The booking ID
	 */
	public int getBookingId() {
		return bookingId;
	}
	/**
	 * Gets the application ID for the booking.
	 * 
	 * @return The application ID
	 */
	public int getApplicationId() {
		return applicationId;
	}
	/**
	 * Gets the NRIC of the officer who is managing the booking.
	 * 
	 * @return The NRIC of the officer
	 */
	public String getOfficerNRIC() {
		return officerNRIC;
	}
	/**
	 * Sets the application ID for the booking.
	 * 
	 * @param applicationId The application ID
	 */
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	/**
	 * Sets the NRIC of the officer who is managing the booking.
	 * 
	 * @param officerNRIC The NRIC of the officer
	 */
	public void setOfficerNRIC(String officerNRIC) {
		this.officerNRIC = officerNRIC;
	}
}
