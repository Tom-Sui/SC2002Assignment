import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class EnquiryService {
    private ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>();
    private HashMap<String, ArrayList<Enquiry>> enquiriesByProject = new HashMap<String, ArrayList<Enquiry>>();
    private HashMap<String, ArrayList<Enquiry>> enquiriesByApplicant = new HashMap<String, ArrayList<Enquiry>>();

    public void refreshEnquiriesByApplicant(String userNric) {
        enquiriesByApplicant.clear();
        for (Enquiry enquiry : viewEnquiriesByApplicant(userNric) ) {
            enquiriesByApplicant.put(enquiry.getUserNric(), enquiry);
        }
    }

    public void refreshEnquiriesByProject(String projectName) {
        enquiriesByProject.clear();
        for (Enquiry enquiry : viewEnquiriesByProject(projectName) ) {
            enquiriesByProject.put(enquiry.getProjectName(), enquiry);
        }
    }
    
    public ArrayList<Enquiry> viewEnquiriesByProject(String projectName) {
        return enquiries.stream()
            .filter(enquiry -> enquiry.getProjectName().equals(projectName))
            .collect(Collectors.toList());
    }

    public ArrayList<Enquiry> viewEnquiriesByProjects(ArrayList<String> projectNames) {
        return enquiries.stream()
            .filter(enquiry -> projectNames.contains(enquiry.getProjectName()))
            .collect(Collectors.toList());
    }

    public ArrayList<Enquiry> viewEnquiriesByApplicant(String userNric) {
        return enquiries.stream()
            .filter(enquiry -> enquiry.getUserNric().equals(userNric))
            .collect(Collectors.toList());
    }

    public ArrayList<Enquiry> viewAllEnquiries() {
        return enquiries;
    } 

    public ArrayList<Enquiry> getEnquiriesByProject(String projectName) {
        refreshEnquiriesByProject(projectName);
        return enquiriesByProject.get(projectName);
    }

    public ArrayList<Enquiry> getEnquiriesByApplicant(String userNric) {
        refreshEnquiriesByApplicant(userNric);
        return enquiriesByApplicant.get(userNric);
    }

    public boolean addEnquiry(Enquiry enquiry) {
        if (enquiry == null) {
            System.out.println("Enquiry cannot be null");
            return false;
        }
        enquiries.add(enquiry);
        return true;
    }

    public boolean deleteEnquiry(Enquiry enquiry) {
        if (enquiry == null) {
            System.out.println("Enquiry cannot be null");
            return false;
        }
        enquiries.remove(enquiry);
        return true;
    }

    public Enquiry getEnquiryById(int enquiryId) {
        return enquiries.stream()
            .filter(enquiry -> enquiry.getEnquiryID() == enquiryId)
            .findFirst()
            .orElse(null);
    }

    public boolean updateEnquiry(Enquiry enquiry) {
        if (enquiry == null) {
            System.out.println("Enquiry cannot be null");
            return false;
        }

        for (int i = 0; i < enquiries.size(); i++) {
            if (enquiries.get(i).getEnquiryID() == enquiry.getEnquiryID()) {
                enquiries.set(i, enquiry);
                return true;
            }
        }

        return false;
    }

    /**
     * Writes all enquiries to a text file named "enquiries.txt".
     * <p>
     * Each enquiry is written in the format:
     * enquiryID,projectName,userNric,message,replyID
     * where replyID is 0 if there is no reply, otherwise it's the actual reply ID.
     * </p>
     */
    public void writeEnquiriesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("enquiries.txt"))) {
            for (Enquiry enquiry : enquiries) {
                int replyId = (enquiry.getReplyID() == 0) ? 0 : enquiry.getReplyID();
                writer.printf("%d,%s,%s,%s,%d%n",
                    enquiry.getEnquiryID(),
                    enquiry.getProjectName(),
                    enquiry.getUserNric(),
                    enquiry.getMessage(),
                    replyId);
            }
            System.out.println("Enquiries have been written to enquiries.txt");
        } catch (IOException e) {
            System.err.println("Error writing enquiries to file: " + e.getMessage());
        }
    }
}

