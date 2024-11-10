package student;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

import camp.Camp;
import committeeMember.CommitteeMember;
import enquiry.BaseEnquiry;
import enquiry.Enquiry;
import user.User;
import enums.Faculty;
import enums.Role;
import reply.Reply;

/**
 *The Student class represents a student user in the system.
 * It extends the User class and implements various interfaces
 * related to registration, withdrawal, and camp-related inquiries.
 * @author Wang Jing
 * @version 1.4
 * @since 2023-11-13
 */
public class Student extends User implements Withdrawable, StudentEnquiry, BaseEnquiry {
     /**
     * A list of camps the student is registered for.
     */    
    private ArrayList<Camp> registeredFor;
    /**
     * A list of inquiries submitted by the student.
     */
    private ArrayList<Enquiry> enquiries;
    /**
     * A counter for generating unique enquiry IDs.
     */
    private int enquiryCounter;

    /**
     * Constructs a new Student object with the given parameters.
     *
     * @param userID   The user ID of the student.
     * @param password The password of the student.
     * @param role     The role of the student.
     * @param name     The name of the student.
     * @param email    The email of the student.
     * @param faculty  The faculty of the student.
     */
    public Student(String userID, String password, Role role, String name, String email, Faculty faculty) {
        super(userID, password, role, name, email, faculty);
        this.registeredFor = new ArrayList<>(); // Initialize with an empty ArrayList
        this.enquiries = new ArrayList<>();
        enquiryCounter = 1;
    }

    // Getter method for registeredFor
    public ArrayList<Camp> getRegisteredFor() {
        return registeredFor;
    }

    // Getter method for enquiries
    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * Set a new EnquiryCounter
     *
     * @param enquiryCounter The new value to be assigned
     */
    public void setEnquiryCounter(int enquiryCounter)
    {
    	this.enquiryCounter = enquiryCounter;
    }
    
    /**
     * Get enquiryCounter
     */
    public int getEnquiryCounter()
    {
    	return enquiryCounter;
    }
    
    /**
     * Adds a camp to the list of registered camp for this student.
     *
     * @param camp The camp to be added.
     */
    public void addCampToRegisteredFor(Camp camp) {
        registeredFor.add(camp);
    }

    /**
     * Removes a camp from the list of registered camp for this student.
     *
     * @param camp The camp to be removed.
     */
    public void removeCampFromRegisteredFor(Camp camp) {
        registeredFor.remove(camp);
    }

    /**
     * Adds a enquiry to the list of enquiries for this student.
     *
     * @param enquiry The enquiry to be added.
     */
    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    /**
     * Removes a enquiry from the list of enquiries for this student.
     *
     * @param enquiry The enquiry to be removed.
     */
    public void removeEnquiry(Enquiry enquiry) {
        enquiries.remove(enquiry);
    }

    /**
     * Method to view available camps to join.
     * It prints details of camps that are visible, not previously withdrawn,
     * and belong to the student's faculty.
     */
    public void viewAvailableCampsToJoin(ArrayList<Camp> allCamps) {
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-15s | %-10s | %-10s | %-10s | %-25s | %-15s |\n", "CampID", "Name", "Location", "Description", "Start Date", "End Date", "Registration Closing Date", "Remaining Slots");
        System.out.println("---------------------------------------------------------------------------");

        for (Camp camp : allCamps) {
            if (camp.getisVisible() && !camp.getPreviouslyWithdrawn().contains(this) && (camp.getDetails().getFaculty().equals(this.getFaculty()) || camp.getDetails().getFaculty().equals(Faculty.NTU))) {
                
		int remainingSlots = camp.calculateRemainingSlots();

                System.out.printf("| %-10s | %-30s | %-15s | %-10s | %-10s | %-10s | %-25s | %-15s |\n",
                camp.getCampID(),
                camp.getDetails().getName(),
                camp.getDetails().getLocation(),
                camp.getDetails().getDescription(),
                camp.getDetails().getStartDate(),
                camp.getDetails().getEndDate(),
                camp.getDetails().getRegistrationClosingDate(),
                remainingSlots);
            }
        }
    }

    /**
     * Method to view registered camps.
     * Display the detials of the camps
     */
    public void viewRegisteredCamps() {
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-15s | %-10s | %-10s | %-10s | %-25s | %-15s |\n", "CampID", "Name", "Location", "Description", "Start Date", "End Date", "Registration Closing Date", "Remaining Slots");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        for (Camp camp : registeredFor) {
            int remainingSlots = camp.calculateRemainingSlots();

            System.out.printf("| %-10s | %-30s | %-15s | %-10s | %-10s | %-10s | %-25s | %-15s |\n",
                camp.getCampID(),
                camp.getDetails().getName(),
                camp.getDetails().getLocation(),
                camp.getDetails().getDescription(),
                camp.getDetails().getStartDate(),
                camp.getDetails().getEndDate(),
                camp.getDetails().getRegistrationClosingDate(),
                remainingSlots);
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }
     /**
     * Method to register a camp as participant.
     * Student would choose a camp and the method would check if the camp is available for registration.
     */ 
    public void register(Camp camp)
    {
    	this.addCampToRegisteredFor(camp);
    }
    
    /**
     * Method to register a camp as participant.
     * Student would choose a camp and the method would check if the camp is available for registration.
     */  
    public void register(Scanner scanner, ArrayList<Camp> allCamps) {

    System.out.println("Enter the CampID to register:");
    String campID = scanner.nextLine();

    Camp selectedCamp = null;

    // Find the camp with the specified CampID
    for (Camp camp : allCamps) {
        if (camp.getCampID().equals(campID)) {
            selectedCamp = camp;
            break;
        }
    }

    // Check if the camp is found and can be registered
    if (selectedCamp != null && selectedCamp.getisVisible() && !selectedCamp.getPreviouslyWithdrawn().contains(this)
            && (selectedCamp.getDetails().getFaculty().equals(this.getFaculty()) || selectedCamp.getDetails().getFaculty().equals(Faculty.NTU))) {
        int remainingSlots = selectedCamp.getDetails().getTotalSlots() - selectedCamp.getParticipants().size();

        // Check if there are available slots and the current date is before the registration closing date
        if (remainingSlots > 0 && selectedCamp.getDetails().getRegistrationClosingDate().isAfter(LocalDate.now())) {
            
            // Check for date clashes with already registered camps
            boolean hasDateClash = false;
            for (Camp registeredCamp : registeredFor) {
                LocalDate registeredStartDate = registeredCamp.getDetails().getStartDate();
                LocalDate registeredEndDate = registeredCamp.getDetails().getEndDate();
                
                if ((selectedCamp.getDetails().getStartDate().isAfter(registeredStartDate) &&
                     selectedCamp.getDetails().getStartDate().isBefore(registeredEndDate)) ||
                    (selectedCamp.getDetails().getEndDate().isAfter(registeredStartDate) &&
                     selectedCamp.getDetails().getEndDate().isBefore(registeredEndDate)) ||
                    selectedCamp.getDetails().getStartDate().isEqual(registeredStartDate) ||
                    selectedCamp.getDetails().getEndDate().isEqual(registeredEndDate)) {
                    
                    hasDateClash = true;
                    break;
                }
            }

            if (!hasDateClash) {
                // Add the student to the camp's participants
                selectedCamp.addParticipant(this);

                // Add the camp to the student's registeredFor list
                addCampToRegisteredFor(selectedCamp);

                System.out.println("Registration successful!");
            } else {
                System.out.println("Cannot register for the camp. Date clashes with already registered camps.");
            }
        } else {
            System.out.println("Cannot register for the camp. Check available slots and registration closing date.");
        }
    } else {
        System.out.println("Camp not found or cannot be registered for.");
    }
}
   

    /**
     * Method to withdraw from a camp with user verification
     * Check if the student is indeed registered to the camp in the first place.
     */  
	@Override
    public void withdraw(Scanner scanner) {

        System.out.print("Enter the CampID to withdraw from: ");
        String campID = scanner.nextLine();

        // Find the camp with the specified CampID in the registeredFor list
        Camp selectedCamp = null;

        for (Camp camp : registeredFor) {
            if (camp.getCampID().equals(campID)) {
                selectedCamp = camp;
                break;
            }
        }

        // Check if the camp is found in the registeredFor list
        if (selectedCamp != null) {
            // Display camp details for verification
            System.out.println("Camp Details:");
            System.out.println("Camp ID: " + selectedCamp.getCampID());
            System.out.println("Camp Name: " + selectedCamp.getDetails().getName());

            // Verify if the user wants to withdraw from the camp
            System.out.println("Do you want to withdraw from this camp? (yes/no)");
            String withdrawalChoice = scanner.nextLine();

            if (withdrawalChoice.equalsIgnoreCase("yes")) {
                // Remove the student from the camp's participants
                selectedCamp.removeParticipant(this);

                // Remove the camp from the student's registeredFor list
                removeCampFromRegisteredFor(selectedCamp);

                // Add the student to the camp's previouslyWithdrawn list
                selectedCamp.addWithdrawn(this);

                System.out.println("Withdrawal successful!");
            } else {
                System.out.println("Withdrawal canceled.");
            }
        } else {
            System.out.println("Camp not found in the registered list. Cannot withdraw.");
        }
    }

	/**
     * Method to submit an enquiry for a specific camp
     */  	
	@Override
    public void submitEnquiry(Scanner scanner, ArrayList<Camp> allCamps) {

        System.out.println("Enter the CampID for the enquiry:");
        String campID = scanner.nextLine();

        Camp selectedCamp = null;

        // Find the camp with the specified CampID
        for (Camp camp : allCamps) {
            if (camp.getCampID().equals(campID)) {
                selectedCamp = camp;
                break;
            }
        }

        // Check if the camp is found
        if (selectedCamp != null) {
            Enquiry newEnquiry = new Enquiry(scanner, selectedCamp, this);

            // Add the enquiry to the student's enquiries list
            enquiries.add(newEnquiry);

            // Add the enquiry to the camp's enquiries list
            selectedCamp.addEnquiry(newEnquiry);

            System.out.println("Enquiry submitted successfully!");
        } else {
            System.out.println("Camp not found. Enquiry submission failed.");
        }
    }

	/**
     * Method to list all enquires submiited by the student 
     */  
	@Override
    public void viewEnquiries() {
        System.out.println("------------------------------------------------------------");
        System.out.println("| Your Enquries                                            |");
        System.out.println("------------------------------------------------------------");
        System.out.println("| Enquiry ID | Date Created | Messages          | Replies  |");
        System.out.println("------------------------------------------------------------");
        
        for (Enquiry enquiry : enquiries) {
        	if (enquiry.getReplies() != null)
        	{
            System.out.printf("| %-11s | %-13s | %-17s | %-8s |\n",
                    enquiry.getEnquiryID(),
                    enquiry.getDateCreated(),
                    enquiry.getContents(),
                    enquiry.getReplies().size());
            // Iterate over replies and print each one
	            for (Reply reply : enquiry.getReplies()) {
	                System.out.println("|   Reply: " + reply.getContents());
	            }
            }	
            else
            {
            	System.out.printf("| %-11s | %-13s | %-17s | %-8s |\n",
                        enquiry.getEnquiryID(),
                        enquiry.getDateCreated(),
                        enquiry.getContents(),
                        0);
            }
        	System.out.println("------------------------------------------------------------");
        }
    }

    /**
     * Method to edit a unprocessed enquiry by EnquiryID. 
     */  
	@Override
	public void editEnquiry(Scanner scanner) {
	
	    System.out.println("Enter the Enquiry ID you want to edit:");
	    String enquiryID = scanner.nextLine();
	
	    for (Enquiry enquiry : enquiries) {
	    	if (enquiry.getEnquiryID().equals(enquiryID) && !enquiry.isProcessed()) {
	            // Display the current enquiry details
	            System.out.println("Current Enquiry Details");
	            System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
	            System.out.println("Date Created: " + enquiry.getDateCreated());
	            System.out.println("Current Message: " + enquiry.getContents());
	
	            // Prompt the user for a new enquiry message
	            System.out.print("Enter the new enquiry message:");
	            String newEnquiryMessage = scanner.nextLine();
	
	            // Update the enquiry message using setEnquiryMessage() method
	            enquiry.setContents(newEnquiryMessage);
	
	            System.out.println("Enquiry updated successfully!");
	            return; // No need to continue searching once found
	        }
	    }
	    System.out.println("Enquiry not found or already processed. Editing failed.");
	}

    /**
     *Method to delete a student-specific enquiry by EnquiryID
     */  
    public void deleteEnquiry(Scanner scanner, ArrayList<Camp> allCamps) {

        System.out.println("Enter the Enquiry ID you want to delete:");
        String enquiryID = scanner.nextLine();

        for (Enquiry enquiry : enquiries) {
            if (enquiry.getEnquiryID().equals(enquiryID) && !enquiry.isProcessed()) {
                // Display the enquiry details
                System.out.println("Enquiry to delete:");
                System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
                System.out.println("Date Created: " + enquiry.getDateCreated());
                System.out.println("Messages: " + enquiry.getContents());

                // Verify if the user wants to delete the enquiry
                System.out.println("Do you want to delete this enquiry? (yes/no)");
                String deleteChoice = scanner.nextLine();

                if (deleteChoice.equalsIgnoreCase("yes")) {
                    // Remove the Enquiry object from the student's enquiries list
                    enquiries.remove(enquiry);

                    // Find the associated camp and remove the Enquiry object from the camp's enquiries list
                    for (Camp camp : allCamps) {
                        for (Enquiry campEnquiry : camp.getEnquiries()) {
                            if (campEnquiry.getEnquiryID().equals(enquiryID)) {
                                camp.removeEnquiry(campEnquiry);
                                break;
                            }
                        }
                    }

                    System.out.println("Enquiry deleted successfully!");
                } else {
                    System.out.println("Enquiry deletion canceled.");
                }

                return; // No need to continue searching once found
            }
        }

        System.out.println("Enquiry not found or already processed. Deletion failed.");
    }

    /**
     *Method to register as Committee Member for a camp 
     *Check if the student has not been register as committee member before
     *Check that the camp is available, add student to camp committee member
     *Change student's role to committee member, set student overseeing camp
     */  
	public CommitteeMember registerAsCM(Scanner scanner, ArrayList<Camp> allCamps, Student student) {
	    // Check if the role is STUDENT
	    if (this.getRole() != Role.STUDENT) {
	        System.out.println("Only students are eligible to register as Committee Members.");
	        return null;
	    }
	
	    System.out.println("Enter the CampID to register as Committee Member:");
	    String campID = scanner.nextLine();
	
	    Camp selectedCamp = null;
	
	    // Find the camp with the specified CampID
	    for (Camp camp : allCamps) {
	        if (camp.getCampID().equals(campID)) {
	            selectedCamp = camp;
	            break;
	        }
	    }
	
	    // Check if the camp is found and can be registered as Committee Member
	    if (selectedCamp != null && selectedCamp.getisVisible() && !selectedCamp.getPreviouslyWithdrawn().contains(this)
	            && (selectedCamp.getDetails().getFaculty().equals(this.getFaculty()) || selectedCamp.getDetails().getFaculty().equals(Faculty.NTU))) {
	    	
	        // Check if there are available committee slots and the current date is before the registration closing date
	        int remainingSlots = selectedCamp.getDetails().getCommitteeSlots() - selectedCamp.getCommittee().size();
	
	        if (remainingSlots > 0 && selectedCamp.getDetails().getRegistrationClosingDate().isAfter(LocalDate.now())) {
	        	CommitteeMember cm = new CommitteeMember(student, selectedCamp);
	            // Add the student to the camp's committee
	            selectedCamp.removeParticipant(this);
	        	selectedCamp.addCommittee(cm);
	
	            System.out.println("Registration as Committee Member successful! Your menu will be changed.");
	            return cm;
	        } else {
	            System.out.println("Cannot register as Committee Member for the camp. Check available committee slots and registration closing date.");
	            return null;
	        }
	    } else {
	        System.out.println("Camp not found or cannot be registered as Committee Member.");
	        return null;
	    }
	}
   
	@Override
    /**
     * Converts the Student object to a TXT format string.
     *
     * @return A string containing TXT-formatted student information.
     */
    public String toString() {
        String delimiter = " | ";
        return super.getUserID() + delimiter + super.getRole() + delimiter + super.getName() + delimiter
                + super.getEmail() + delimiter + super.getFaculty();
    }

    /**
     * Generates CSV headers for the Student class.
     *
     * @return A string containing CSV headers.
     */
    public static String generateCSVHeaders() {
        String delimiter = ", ";
        return "UserID" + delimiter + "Role" + delimiter + "Name" + delimiter
                + "Email" + delimiter + "Faculty";
    }

    /**
     * Converts the Student object to a CSV format string.
     *
     * @return A string containing CSV-formatted student information.
     */
    public String toCSV() {
        String delimiter = ", ";
        return super.getUserID() + delimiter + super.getRole() + delimiter + super.getName() + delimiter
                + super.getEmail() + delimiter + super.getFaculty();
    }
}

