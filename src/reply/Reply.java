package reply;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import enquiry.Enquiry;
import user.User;
import file.Input;

/**
 *The Reply class represents that CommitteeMember reply to enquiry in the system.
 * It extends the Enquiry class
 * related to enquiry matters.
 * @author Derrick 
 * @version 1.4
 */

public class Reply {
	private static int idCount;
	private String enquiryID;	  
	private String replyID;
	private LocalDate dateCreated;
	private LocalTime timeCreated; 
	private User sendor;
	private String contents;
  
	/**
     * Method to take in string inputs.
     * It prints details of reply,
     * and process it into the system for fileIO.
     */
    public Reply(Scanner scanner, Enquiry enquiry, User user) {
	    idCount++;
	    this.replyID = "REP" + idCount;
	    this.enquiryID = enquiry.getEnquiryID();
	    this.dateCreated = LocalDate.now();
	    this.timeCreated = LocalTime.now();
	    this.sendor = user;
	    this.contents = Input.getStringInput("Enter the contents of your Reply: ", scanner);
	    enquiry.setProcessed(true);
	    enquiry.addReply(this);
    }
 
 // Getter method for getEnquiryID
    public String getEnquiryID()
    {
  	  return enquiryID;
    }
 // Getter method for getReplyID
    public String getReplyID()
	{
    	return replyID;
	}
 // Getter method for getContents
    public String getContents()
    {
    	return contents;
    }
}
