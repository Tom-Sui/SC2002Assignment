import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * An abstract base class representing a generic user in the system.
 * Provides common properties and methods for all user types including
 * authentication, profile management, and enquiry handling.
 */
public abstract class User {
    private String name;
    private String NRIC;
    private int userID;
    private String password;
    private int age;
    private MaritalStatus maritalStatus;

    /**
     * Default constructor initializes all fields with default values.
     */
    public User() {
        this("null", "null", -1, "null", 0, MaritalStatus.SINGLE);
    }

    /**
     * Parameterized constructor for creating a User with specific attributes.
     * 
     * @param name The user's full name
     * @param NRIC The user's National Registration Identity Card number
     * @param userID The unique identifier for the user
     * @param password The user's login password (will be hashed)
     * @param age The user's age
     * @param maritalStatus The user's marital status (SINGLE or MARRIED)
     */
    public User(String name, String NRIC, int userID, String password, int age, MaritalStatus maritalStatus) {
        this.name = name;
        this.NRIC = NRIC;
        this.userID = userID;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    // Setter methods with Javadoc

    /**
     * Sets the user's name.
     * @param name The new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the user's NRIC.
     * @param NRIC The new NRIC to set
     */
    public void setNRIC(String NRIC) {
        this.NRIC = NRIC;
    }

    /**
     * Sets the user's ID.
     * @param UserID The new user ID to set
     */
    public void setUserID(int UserID) {
        this.userID = UserID;
    }

    /**
     * Sets the user's password (should be hashed before storage).
     * @param password The new password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the user's age.
     * @param age The new age to set
     */
    public void setage(int age) {
        this.age = age;
    }

    /**
     * Sets the user's marital status.
     * @param maritalStatus The new marital status (SINGLE or MARRIED)
     */
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    // Getter methods with Javadoc

    /**
     * Gets the user's name.
     * @return The user's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the user's NRIC.
     * @return The user's NRIC
     */
    public String getNRIC() {
        return this.NRIC;
    }

    /**
     * Gets the user's ID.
     * @return The user's ID
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Gets the user's age.
     * @return The user's age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Gets the user's marital status.
     * @return The user's marital status
     */
    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    /**
     * Authenticates the user by comparing hashed password input with stored password.
     * 
     * @param password The password to verify
     * @return true if authentication succeeds, false otherwise
     */
    public boolean login(String password) {
        PasswordHashing passwordHashing = new PasswordHashing();
        String hashedPassword = passwordHashing.hashingPassword(password);
        if (this.password.equals(hashedPassword)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Placeholder method for enquiry creation (to be implemented by subclasses).
     * @return Always returns false in base implementation
     */
    public boolean createEnquiry() {
        return false;
    }

    /**
     * Modifies user information in the specified file.
     * 
     * @param filePath The path to the file containing user data
     * @param target The field to modify (name, NRIC, age, maritalStatus, or password)
     * @return true if modification succeeds, false otherwise
     */
    public boolean editProfile(String filePath, int target) {
        String fileContent = "";
        String[] buffer;
        Scanner sc = new Scanner(System.in);

        File file = new File(filePath);
        PasswordHashing passwordHashing = new PasswordHashing();

        try {
            Scanner scanner = new Scanner(file);
            // fileContent = fileContent + scanner.nextLine() + "\n";
            while (scanner.hasNextLine()) {
                buffer = scanner.nextLine().split(",");
                if (buffer[2].equals(this.NRIC)) {
                    switch (target) {
                        case 1:
                            System.out.print("Enter new name: ");
                            buffer[1] = sc.nextLine();
                            this.name = buffer[1];
                            break;
                        case 2:
                            System.out.print("Enter new NRIC: ");
                            String NRIC = sc.nextLine();
                            while (true) {
                                if (NRIC.length() != 9) {
                                    System.out.print("Invalid NRIC. Please enter a valid NRIC: ");
                                    NRIC = sc.nextLine();
                                } else {
                                    if(NRIC.charAt(0) == 'T' || NRIC.charAt(0) == 'S') {
                                        break;
                                    } else {
                                        System.out.print("Invalid NRIC. Please enter a valid NRIC: ");
                                        NRIC = sc.nextLine();
                                    }
                                }
                            }
                            System.out.print("Enter new NRIC: ");
                            buffer[2] = sc.nextLine();
                            this.NRIC = buffer[2];
                            break;
                        case 3:
                            System.out.print("Enter new age: ");
                            while(true) {
                                try {
                                    buffer[3] = sc.nextLine();
                                    this.age = Integer.parseInt(buffer[3]);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.print("Invalid input. Please enter a valid age: ");
                                }
                            }
                            System.out.println("\n===Age change success===\n");
                            break;
                        case 4:
                            System.out.print("Enter new marital status (single/married): ");
                            
                            buffer[4] = sc.nextLine().toLowerCase();
                            if (buffer[4].equals("single")) {
                                this.maritalStatus = MaritalStatus.SINGLE;
                            } else if (buffer[4].equals("married")) {
                                this.maritalStatus = MaritalStatus.MARRIED;
                            }
                            System.out.println("\n===Marital status change success===\n");
                            break;
                        case 5:
                            System.out.println("Enter new password: ");
                            buffer[5] = passwordHashing.hashingPassword(sc.nextLine());
                            this.password = buffer[5];
                            System.out.println("\n===Password change success===\n");
                            break;
                        default:
                            break;
                    }
                }
                for (int i = 0; i < buffer.length; i++) {
                    fileContent = fileContent + buffer[i] + ",";
                }
                fileContent = fileContent + "\n";
            }
            FileWriter writer = new FileWriter(filePath);
            writer.write(fileContent);
            scanner.close();
            writer.close();

            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Error occured when reading " + filePath);
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println("Error occured when writing " + filePath);
            e.printStackTrace();
            return false;
        }
    }

    // Abstract methods that must be implemented by subclasses

    /**
     * Creates a new enquiry for a specific project.
     * 
     * @param project The project the enquiry relates to
     * @param message The enquiry message content
     * @return The created Enquiry object
     */
    public abstract Enquiry createEnquiry(Project project, String message);

    /**
     * Retrieves all enquiries associated with this user.
     * @return An array of Enquiry objects
     */
    public abstract Enquiry[] viewEnquiries();

    /**
     * Modifies an existing enquiry.
     * 
     * @param enquiry The enquiry to modify
     * @param newMessage The new message content
     */
    public abstract void editEnquiry(Enquiry enquiry, String newMessage);

    /**
     * Deletes an existing enquiry.
     * @param enquiry The enquiry to delete
     */
    public abstract void deletEnquiry(Enquiry enquiry);
}