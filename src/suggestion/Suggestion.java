package suggestion;

import java.time.LocalDate;
import java.util.Scanner;
import committeeMember.CommitteeMember;

/**
 *The Suggestion class is a part of the CommitteeMember fucntion in the system.
 * It extends from the CommitteeMember class and implements various interfaces
 * related to camp-related inquiries.
 * @author Derrick 
 * @version 1.4
 */

public class Suggestion {
  private static int idCount;
  private String suggestionID;
  private LocalDate dateCreated;
  private CommitteeMember createdBy;
  private String content;
  private boolean isProcessed;


  public Suggestion(Scanner sc, CommitteeMember aCM) {
    idCount++;
    this.suggestionID = "SUG" + (idCount);
    this.dateCreated = LocalDate.now();
    this.createdBy = aCM;
    this.content = file.Input.getStringInput("Enter your suggestions: ", sc);
    this.isProcessed = false;
  }

  /**
   * Getter for suggestionID
   */
  public String getSuggestionID() {
    return suggestionID;
  }

  /**
   * Set a new setSuggestionID
   *
   * @param suggestionID The new value to be assigned
   */
  public void setSuggestionID(String suggestionID) {
    this.suggestionID = suggestionID;
  }

  /**
   * Getter for campID
   */
  public String getCampID() {
    return createdBy.getOverseeingCamp().getCampID();
  }

  /**
   * Getter for dateCreated
   */  
  public LocalDate getDateCreated() {
    return dateCreated;
  }

   /**
    * Set a new setDateCreated
    *
    * @param dateCreated the new LocalDate to replace value of dateCreated
    */
  public void setDateCreated(LocalDate dateCreated) {
    this.dateCreated = dateCreated;
  }

  /**
   * Get the CommitteeMember that created it
  */
  public CommitteeMember getCreatedBy() {
    return createdBy;
  }

  /**
   * Set a new setCreatedBy
   *
   * @param createdBy Change the suggestion createdBy value
   */
  public void setCreatedBy(CommitteeMember createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Getter for createdBy Enquiry
   */
  public String getCreatorName() {
    return createdBy.getName();
  }

  /**
   * Getter for content of enquiry
   */
  public String getContent() {
    return content;
  }

  /**
   * Setter a new setContent
   *
   * @param content User's new content
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Getter to check if Enquiry has been processed
   */
  public boolean isProcessed() {
    return isProcessed;
  }

  /**
   * Setter for isProcessed
   *
   * @param isProcessed Updated value for isProcessed
   */
  public void setProcessed(boolean isProcessed) {
    this.isProcessed = isProcessed;
  }

  /**
   * Call for Committee Member that created this to increase in their point
   */
  public void incrementPoint() {
    this.createdBy.incrementPoint();
  }
  
  @Override
  public String toString()
  {
	  String delimiter = " | ";
	  return this.suggestionID + delimiter + this.dateCreated + delimiter + this.createdBy + delimiter + this.content + delimiter + this.isProcessed + delimiter;
  }
}
