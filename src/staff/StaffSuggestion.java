package staff;

import java.util.Scanner;
import suggestion.BaseSuggestion;

/**
 * Represents the StaffSuggestion interface. Abstract methods relating to the interactions
 * with the <code>Staff</code> and <code>Suggestion</code> class.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 * @see <code>Staff</code>
 */
public interface StaffSuggestion extends BaseSuggestion {
  
  /**
   * Displays all suggestions belonging to a camp.
   * 
   * @see <code>Suggestion</code>
   */
  public void viewSuggestions();
  
  /**
   * Approves a suggestions belonging to a camp.
   * 
   * @param sc The scanner object to be injected.
   * @see <code>Suggestion</code>
   */
  public void approve(Scanner sc);
}