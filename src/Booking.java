
public class Booking {
	private static int bookingId = 1;
	private int applicationId;
	private String officerNRIC;
	
	public Booking() {
		Booking.bookingId++;
	};
	
	public Booking(int applicationId, String officerNRIC) {
		this.applicationId = applicationId;
		this.officerNRIC = officerNRIC;
	}
	
	public int getBookingId() {
		return bookingId;
	}
	public int getApplicationId() {
		return applicationId;
	}
	public String getOfficerNRIC() {
		return officerNRIC;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	
	public void setOfficerNRIC(String officerNRIC) {
		this.officerNRIC = officerNRIC;
	}
}
