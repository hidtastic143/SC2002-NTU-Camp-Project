package enquiry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import camp.Camp;
import reply.Reply;
import student.Student;

/**
 * The Enquiry class represents a user's inquiry about a specific camp.
 * It contains information about the enquirer, the creation date, and any replies.
 * @author Wang Jing
 * @version 1.4
 * @since 2023-11-13
 */
public class Enquiry {

  /** The unique ID assigned to the enquiry. */
  private String enquiryID;

  /** The ID of the camp associated with the enquiry. */
  private String campID;

  /** The date when the enquiry was created. */
  private LocalDate dateCreated;

  /** The name of the student making the enquiry. */
  private String enquirerName;

  /** The message content of the enquiry. */
  private String enquiryMessage;
  
  /** List of replies to the enquiry. */
  private ArrayList<Reply> replies;

  /** Flag indicating whether the enquiry has been processed. */
  private boolean isProcessed = false;
  
    /**
     * Constructs a new Enquiry object with the specified parameters.
     *
     * @param sc	Scanner will be used to receive enquiry contents
     * @param aCamp this enquiry will be assigned to the aCamp
     * @param std	Student class that is assigned to the enquiry created
     */
  public Enquiry(Scanner sc, Camp aCamp, Student std) {
    this.enquiryID = std.getUserID() + (std.getEnquiryCounter() + 1);
    std.setEnquiryCounter(std.getEnquiryCounter() + 1);
    this.dateCreated = LocalDate.now();
    this.enquirerName = std.getName();
    this.campID = aCamp.getCampID();
    this.enquiryMessage = file.Input.getStringInput("Enter the contents of your enquiry: ", sc);
    this.replies = new ArrayList<Reply>();
    this.isProcessed = false;
  }

   // Getter methods
  public String getEnquiryID() {
    return enquiryID;
  }
  
  public String getCampID() {
    return campID;
  }

  public LocalDate getDateCreated() {
    return dateCreated;
  }

  public void setEnquirerName(String enquirerName)
  {
	  this.enquirerName = enquirerName;
  }
  
  public String getEnquirerName()
  {
	  return enquirerName;
  }
  
  public String getContents() {
    return enquiryMessage;
  }
//setter method
  public void setContents(String contents) {
    this.enquiryMessage = contents;
  }

  public ArrayList<Reply> getReplies() {
    if (this.replies.isEmpty()) {
      return null;
    }
    else {
      return replies;
    }
  }
    /**
     * Adds a reply to the list of replies for this enquiry.
     *
     * @param reply The reply to be added.
     */
  public void addReply(Reply reply) {
    this.replies.add(reply);
  }
  
  public boolean isProcessed() {
    return isProcessed;
  }

  public void setProcessed(boolean isProcessed) {
    this.isProcessed = isProcessed;
  }
  
  /**
   * Converts the Enquiry object to a TXT format string.
   *
   * @return A string containing TXT-formatted enquiry information.
   */
  public String toString() {
      String delimiter = " | ";
      return this.campID + delimiter + this.dateCreated + delimiter + this.enquirerName + delimiter
              + this.enquiryID + delimiter + this.enquiryMessage + delimiter + this.isProcessed;
  }

  /**
   * Generates CSV headers for the Enquiry class.
   *
   * @return A string containing CSV headers.
   */
  public static String generateCSVHeaders() {
      String delimiter = ", ";
      return "CampID" + delimiter + "DateCreated" + delimiter + "EnquirerName" + delimiter
              + "EnquiryID" + delimiter + "EnquiryMessage" + delimiter + "IsProcessed";
  }

  /**
   * Converts the Enquiry object to a CSV format string.
   *
   * @return A string containing CSV-formatted enquiry information.
   */
  public String toCSV() {
      String delimiter = ", ";
      return this.campID + delimiter + this.dateCreated + delimiter + this.enquirerName + delimiter
              + this.enquiryID + delimiter + this.enquiryMessage + delimiter + this.isProcessed;
  }
}

