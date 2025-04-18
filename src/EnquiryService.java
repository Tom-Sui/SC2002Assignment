public class EnquiryService {
    private ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>();

    public ArrayList<Enquiry> viewEnquiriesByProject(Project project) {
        return enquiries.stream()
            .filter(enquiry -> enquiry.getProject().equals(project))
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
}

