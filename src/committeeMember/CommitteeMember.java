package committeeMember;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner; // Import the Scanner class

import camp.Camp;
import enquiry.Enquiry;
import enquiry.ReplyEnquiry;
import enums.Role;
import enums.RoleFilter;
import enums.DateFilter;
import enums.Faculty;
import enums.Format;
import file.Input;
import reply.Reply;
import report.BaseReport;
import student.Student;
import suggestion.*;

/**
 *The CommitteeMember class represents a student user in the system.
 * It extends the User class and implements various interfaces
 * related to registration, withdrawal, and camp-related inquiries.
 * @author Derrick 
 * @version 1.4
 */
public class CommitteeMember extends Student
    implements ReplyEnquiry, CommitteeMemberSuggestion, BaseReport {
  Camp overseeing;
  HashMap<String, Suggestion> suggestionList;
  int points;

  // Constructor to initialize attributes
  public CommitteeMember(Student student, Camp camp) {
    super(student.getUserID(), student.getPassword(), Role.COMMITTEE_MEMBER, student.getName(),
        student.getEmail(), student.getFaculty());
    overseeing = camp;
    points = 0;
    suggestionList = new HashMap<String, Suggestion>();
    for (int i = 0; i < student.getRegisteredFor().size(); i++) {
      super.addCampToRegisteredFor(student.getRegisteredFor().get(i));
    }
    for (int i = 0; i < student.getEnquiries().size(); i++) {
      super.addEnquiry(student.getEnquiries().get(i));
    }
  }
  
  /**
   * Constructs a new CommitteeMember object with the given parameters.
   
   * @param userID   The user ID of the student.
   * @param password The password of the student.
   * @param name     The name of the student.
   * @param email    The email of the student.
   * @param faculty  The faculty of the student.
   */
  public CommitteeMember(String userID, String password, String name, String email,
      Faculty faculty) {
    super(userID, password, Role.COMMITTEE_MEMBER, name, email, faculty);
    overseeing = null;
    points = 0;
    suggestionList = new HashMap<String, Suggestion>();
  }

  /**
   * Get the user ID from the User super class.
   */
  public String getUserID() {
    return super.getUserID();
  }
  /**
   * Get the password from the password super class.
   */
  public String getPassword() {
    return super.getPassword();
  }
  /**
  * Get the Name from the getName super class.
  */
  public String getName() {
    return super.getName();
  }
  /**
   * Get the Role from the Role super class.
   */
  public Role getRole() {
    return super.getRole();
  }
  /**
   * Get the Email from the getEmail super class.
   */
  public String getEmail() {
    return super.getEmail();
  }
  /**
   * Get the Role from the getFaculty super class.
   */
  public Faculty getFaculty() {
    return super.getFaculty();
  }
  /**
   * Get the camp overseeing assign.
   */
  public Camp getOverseeingCamp() {
    return overseeing;
  }
  /**
   * Increase the points for the CommitteeMember.
   */
  public void incrementPoint() {
    this.points++;
  }
  /**
   * Get the points from the point system.
   */
  public int getPoints() {
    return points;
  }

  // Setter methods for attributes
  public void setUserID(String userID) {
    super.setUserID(userID);
  }
  // Setter methods for attributes
  public void setPassword(String password) {
    super.setPassword(password);
  }
  // Setter methods for attributes
  public void setName(String name) {
    super.setName(name);;
  }
  // Setter methods for attributes
  public void setOverseeingCamp(Camp camp) {
    overseeing = camp;
  }


  public void registerCamp(Scanner scanner, ArrayList<Camp> campList) {
    super.register(scanner, campList);
  }

  // Method to withdraw a student from a registered camp
  public void withdrawCamp(Scanner scanner) {
    super.withdraw(scanner);
  }

  /**
   * Display the enquiries
   * check for if user is committeeMember 
   * Display the @para for the all the enquiries
   */  
  @Override
  public void viewEnquiries() {
    if (overseeing == null)
      System.out.println("Please join a camp as a committee member first.");
    else {
      ArrayList<Enquiry> results = overseeing.getEnquiries();
      if (!results.isEmpty()) {
        String delimiter = "-";
        String paddingParameters = "| %-10s | %-25s | %-20s | %-10s | %-40s | %-10s | \n";
        System.out.println("Your Camp's Enquiries");
        System.out.println(delimiter.repeat(130));
        System.out.printf(paddingParameters, "CampID", "Date Created", "EnquirerName", "EnquiryID",
            "EnquiryMessage", "IsProcessed");
        System.out.println(delimiter.repeat(130));

        for (Enquiry e : results) {
          e.toString();
        }

        System.out.println(delimiter.repeat(130));
        System.out.println();
      } else
        System.out.println("Current camp has no enquiries to show. Check back later.");
    }
    super.viewEnquiries();
  }
  /**
   * Method to withdraw from a camp with user verification
   * Check if the student is indeed registered to the camp in the first place.
   */
  @Override
  public void withdraw(Scanner scanner) {
    System.out.println("Enter the CampID to withdraw from:");
    String campID = scanner.nextLine();

    Camp selectedCamp = null;

    for (Camp camp : super.getRegisteredFor()) {
      if (camp.getCampID().equals(campID)) {
        selectedCamp = camp;
        break;
      }
    }

    // Check if the camp is found in the registeredFor list
    if (selectedCamp != null) {
      if (selectedCamp.equals(overseeing)) {
        System.out.println("Cannot withdraw from camp " + selectedCamp.getCampID()
            + " as you are a Committee Member for this camp. Withdrawal cancelled");
        return;
      }

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
        System.out.println("Withdrawal cancelled.");
      }
    } else {
      System.out.println("Camp not found in the registered list. Cannot withdraw.");
    }
  }
  /**
   * Method to edit a unprocessed enquiry by EnquiryID. 
   */ 
  @Override
  public void editEnquiry(Scanner sc) {
    super.editEnquiry(sc);
  }
  /**
   * Method to reply to a processed enquiry by EnquiryID. 
   */
  @Override
  public void replyEnquiry(Scanner sc) {
    ArrayList<Enquiry> enquiries = this.overseeing.getEnquiries();
    String enquiryID = Input.getStringInput("Enter enquiryID of enquiry to edit: ", sc);
    for (Enquiry e : enquiries) {
      if (e.getEnquiryID().equals(enquiryID)) {
        Reply reply = new Reply(sc, e, this);
        e.addReply(reply);
        incrementPoint();
        System.out.println("Enquiry " + enquiryID + " replied.");
      }
    }
    System.out.println("EnquiryID  " + enquiryID
        + " provided not found in this camp's list of enquiries, please try again.");
  }
  /**
   * Method to list all suggestion submiited by the committeeMember 
   */  
  @Override
  public void viewSuggestions() {
    // View a list of all suggestions
    System.out.println("------------------ Suggestion List ------------------");
    for (Map.Entry<String, Suggestion> suggestionMap : suggestionList.entrySet()) {
      System.out.println(suggestionMap.getValue().getSuggestionID() + " - "
          + suggestionMap.getValue().getCreatorName());
      System.out.println("Suggestion contents : " + suggestionMap.getValue().getContent());
      System.out.println();
    }
    System.out.println("--------------- End of Suggestion List --------------");
  }
  /**
   * Method to submit suggestion for a specific camp
   */  
  @Override
  public void submitSuggestion(Scanner scanner) {
    Suggestion suggestion = new Suggestion(scanner, this);
    this.overseeing.addSuggestion(suggestion);
    suggestionList.put(suggestion.getSuggestionID(), suggestion);
    incrementPoint();

    System.out.println("Suggestion successfully implemented!");
  }
  /**
   * Method to edit a unprocessed suggestion by suggestionID. 
   */
  @Override
  public void editSuggestion(Scanner scanner) {

    // Prompt the user to select a suggestion to edit
    System.out.print("Please select the suggestion ID you want to edit:");
    String suggestionID = scanner.nextLine();

    if (this.suggestionList.containsKey(suggestionID)
        && !this.suggestionList.get(suggestionID).isProcessed()) {
      // Allow the user to edit the suggestion details
      System.out.print("Enter the updated suggestion content:");
      String updatedContent = scanner.nextLine();
      this.suggestionList.get(suggestionID).setContent(updatedContent);

      // Inform the user about the successful update
      System.out.println("Suggestion " + suggestionID + " has been updated successfully.");
    } else
      System.out
          .println("Suggestion " + suggestionID + " do not exist / already processed. Try Again.");
  }
  /**
   *Method to delete a committeeMember-specific suggestion by suggestionID
   */
  @Override
  public void deleteSuggestion(Scanner scanner) {

    // Prompt the user to select a suggestion to delete
    System.out.print("Please select the suggestion ID you want to delete: ");
    String suggestionID = scanner.nextLine();

    if (this.suggestionList.containsKey(suggestionID)) {
      suggestionList.remove(suggestionID);

      // Inform the user about the successful deletion
      System.out.println("Suggestion " + suggestionID + " has been deleted successfully.");
    } else
      System.out.println("Suggestion " + suggestionID + " do not exist. Try Again.");
  }

  /**
   * Generates a report of participants with filters based on role belonging to the camp created by
   * this staff. Option to save given after report generation.
   * 
   * @param sc Scanner object to be injected.
   */
  @Override
  public void generateParticipantReport(Scanner sc) {
    // early exit if no camp created

    ArrayList<Student> students = this.overseeing.getParticipants();
    ArrayList<CommitteeMember> committee = this.overseeing.getCommittee();

    Format formatSelection = enums.Format.getFormatFromStringInput(sc);
    String filterYesOrNo = file.Input
        .getStringInput("Do you wish to filter the report by role?: (y/n) ", sc).toLowerCase();
    if (filterYesOrNo.equals("y")) {
      RoleFilter filterSelection = enums.RoleFilter.getRoleFilterFromStringInput(sc);
      switch (filterSelection) {

        case STUDENT: {
          if (formatSelection == Format.CSV) {
            System.out.println(Student.generateCSVHeaders());
            for (Student s : students) {
              System.out.println(s.toCSV());
            }
          } else {
            for (Student s : students) {
              System.out.println(s.toString());
            }
          }
          break;
        }

        case COMMITTEE_MEMBER: {
          if (formatSelection == Format.CSV) {
            System.out.println(Student.generateCSVHeaders());
            for (CommitteeMember cm : committee) {
              System.out.println(cm.toCSV().substring(0, cm.toCSV().length() - 3));
            }
          } else {
            for (CommitteeMember cm : committee) {
              System.out.println(cm.toString().substring(0, cm.toString().length() - 3));
            }
          }
          break;
        }
      }
      String saveYesOrNo = file.Input
          .getStringInput("Do you wish to save the report as a file?: (y/n) ", sc).toLowerCase();
      if (saveYesOrNo.equals("y")) {
        String fileName = file.Input.getStringInput(
            "Please enter the name of the output file (do not include file extension): ", sc);
        ArrayList<String> toSave = new ArrayList<>();
        switch (filterSelection) {
          case STUDENT: { // filter student
            if (formatSelection == Format.CSV) {
              toSave.add(student.Student.generateCSVHeaders());
              for (Student s : students) {
                toSave.add(s.toCSV());
              }
            } else {
              for (Student s : students) {
                toSave.add(s.toString());
              }
            }
            break;
          }
          case COMMITTEE_MEMBER: {
            if (formatSelection == Format.CSV) {
              toSave.add(Student.generateCSVHeaders());
              for (CommitteeMember cm : committee) {
                toSave.add(cm.toCSV().substring(0, cm.toCSV().length() - 3));
              }
            } else {
              for (CommitteeMember cm : committee) {
                toSave.add(cm.toString().substring(0, cm.toString().length() - 3));
              }
            }
            break;
          }
        }
        file.FileIO.writeToFile(formatSelection, fileName, toSave);
        System.out.println("File saved.");
      }
    } else {
      if (formatSelection == Format.CSV) {
        System.out.println(Student.generateCSVHeaders());
        for (CommitteeMember cm : committee) {
          System.out.println(cm.toCSV().substring(0, cm.toCSV().length() - 3));
        }
        for (Student s : students) {
          System.out.println(s.toCSV());
        }
      } else {
        for (CommitteeMember cm : committee) {
          System.out.println(cm.toString().substring(0, cm.toString().length() - 4));
        }
        for (Student s : students) {
          System.out.println(s.toString());
        }
      }
      String saveYesOrNo = file.Input
          .getStringInput("Do you wish to save the report as a file?: (y/n) ", sc).toLowerCase();
      if (saveYesOrNo.equals("y")) {
        String fileName = file.Input.getStringInput(
            "Please enter the name of the output file (do not include file extension): ", sc);
        ArrayList<String> toSave = new ArrayList<>();
        if (formatSelection == Format.CSV) {
          toSave.add(Student.generateCSVHeaders());
          for (CommitteeMember cm : committee) {
            toSave.add(cm.toCSV().substring(0, cm.toCSV().length() - 3));
          }
          for (Student s : students) {
            toSave.add(s.toCSV());
          }
        } else {
          for (CommitteeMember cm : committee) {
            toSave.add(cm.toString().substring(0, cm.toCSV().length() - 3));
          }
          for (Student s : students) {
            toSave.add(s.toString());
          }
        }
        file.FileIO.writeToFile(formatSelection, fileName, toSave);
        System.out.println("File saved.");
      }
    }
  }

  /**
   * Generates a report of enquiries with filters based on date belonging to the camp created by
   * this staff. Option to save given after report generation.
   * 
   * @param sc Scanner object to be injected.
   */
  @Override
  public void generateEnquiryReport(Scanner sc) {
    // early exit if no camp created
    ArrayList<Enquiry> unfilteredResult = this.overseeing.getEnquiries();
    ArrayList<Enquiry> filteredResult = new ArrayList<>();
    Format formatSelection = enums.Format.getFormatFromStringInput(sc);
    String filterYesOrNo = file.Input
        .getStringInput("Do you wish to filter the report by date?: (y/n) ", sc).toLowerCase();
    if (filterYesOrNo.equals("y")) {
      DateFilter filterSelection = enums.DateFilter.getDateFilterFromStringInput(sc);
      // generate report with filters base on format selection
      switch (filterSelection) {
        case ON: {
          LocalDate dateQuery = file.Input.getDateFromIntInputs("your filter: ", sc);
          for (Enquiry e : unfilteredResult) {
            if (e.getDateCreated().equals(dateQuery))
              filteredResult.add(e);
          }
          break;
        }
        case BEFORE: {
          LocalDate dateQuery = file.Input.getDateFromIntInputs("your filter: ", sc);
          for (Enquiry e : unfilteredResult) {
            if (e.getDateCreated().isBefore(dateQuery))
              filteredResult.add(e);
          }
          break;
        }
        case AFTER: {
          LocalDate dateQuery = file.Input.getDateFromIntInputs("your filter: ", sc);
          for (Enquiry e : unfilteredResult) {
            if (e.getDateCreated().isAfter(dateQuery))
              filteredResult.add(e);
          }
          break;
        }
      }
      if (formatSelection == Format.CSV) {
        System.out.println(Enquiry.generateCSVHeaders());
        for (Enquiry e : filteredResult) {
          System.out.println(e.toCSV());
        }

      } else {
        for (Enquiry e : filteredResult) {
          System.out.println(e.toString());
        }
      }

    } else {
      if (formatSelection == Format.CSV) {
        System.out.println(Enquiry.generateCSVHeaders());
        for (Enquiry e : unfilteredResult) {
          System.out.println(e.toCSV());
        }

      } else {
        for (Enquiry e : unfilteredResult) {
          System.out.println(e.toString());
        }
      }
    }
    String saveYesOrNo = file.Input
        .getStringInput("Do you wish to save the report as a file?: (y/n) ", sc).toLowerCase();
    if (saveYesOrNo.equals("y")) {
      String fileName = file.Input.getStringInput(
          "Please enter the name of the output file (do not include file extension): ", sc);
      ArrayList<String> toSave = new ArrayList<>();
      if (filterYesOrNo.equals("y")) {
        if (formatSelection == Format.CSV) {
          toSave.add(Enquiry.generateCSVHeaders());
          for (Enquiry e : filteredResult) {
            toSave.add(e.toCSV());
          }
        } else {
          for (Enquiry e : filteredResult) {
            toSave.add(e.toString());
          }
        }
        file.FileIO.writeToFile(formatSelection, fileName, toSave);
        System.out.println("File saved.");
      } else {
        if (formatSelection == Format.CSV) {
          toSave.add(Enquiry.generateCSVHeaders());
          for (Enquiry e : unfilteredResult) {
            toSave.add(e.toCSV());
          }
        } else {
          for (Enquiry e : unfilteredResult) {
            toSave.add(e.toString());
          }
        }
        file.FileIO.writeToFile(formatSelection, fileName, toSave);
        System.out.println("File saved.");
      }
    }
  }

  /**
   * Converts the Student object to a TXT format string.
   *
   * @return A string containing TXT-formatted student information.
   */
  @Override
  public String toString() {
    String delimiter = " | ";
    return super.getUserID() + delimiter + super.getRole() + delimiter + super.getName() + delimiter
        + super.getEmail() + delimiter + super.getFaculty() + delimiter + this.points + delimiter;
  }

  /**
   * Generates CSV headers for the Student class.
   *
   * @return A string containing CSV headers.
   */
  public static String generateCSVHeaders() {
    String delimiter = ", ";
    return "UserID" + delimiter + "Role" + delimiter + "Name" + delimiter + "Email" + delimiter
        + "Faculty" + delimiter + "Points";
  }

  /**
   * Converts the Student object to a CSV format string.
   *
   * @return A string containing CSV-formatted student information.
   */
  public String toCSV() {
    String delimiter = ", ";
    return super.getUserID() + delimiter + super.getRole() + delimiter + super.getName() + delimiter
        + super.getEmail() + delimiter + super.getFaculty() + delimiter + this.points;
  }
}


