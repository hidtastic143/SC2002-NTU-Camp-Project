package committeeMember;

import java.util.Scanner;
import suggestion.BaseSuggestion;

/**
 * Represents the CommitteeMemberSuggestion interface. Abstract methods relating to the interactions
 * with the <code>Suggestion</code> class are included here and must be implemented by the
 * <code>CommitteeMember</code> class.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 * @see <code>CommitteeMember</code>, <code>Suggestion</code>
 */
public interface CommitteeMemberSuggestion extends BaseSuggestion {

  /**
   * Displays all suggestions belonging to the camp overseen by a committee member. Must be
   * implemented by the <code>CommitteeMember</code> class.
   * 
   * @see <code>CommitteeMember</code>, <code>Suggestion</code>
   */
  public void viewSuggestions();

  /**
   * Submits a suggestion for the camp overseen by a committee member. Must be implemented by the
   * <code>CommitteeMember</code> class.
   * 
   * @param sc the Scanner object to be injected
   * @see <code>CommitteeMember</code>, <code>Suggestion</code>
   */
  public void submitSuggestion(Scanner sc);

  /**
   * Edits a submitted suggestion for the camp overseen by a committee member. Additionally, the suggestion cannot already be processed. Must
   * be implemented by the <code>CommitteeMember</code> class.
   * @param sc the Scanner object to be injected
   * @see <code>CommitteeMember</code>, <code>Suggestion</code>
   */
  public void editSuggestion(Scanner sc);
  /**
   * Deletes a submiteed suggestion for the camp overseen by a committee member. Additionally, the suggestion cannot already be processed. Must
   * be implemented by the <code>CommitteeMember</code> class.
   * @param sc the Scanner object to be injected
   * @see <code>CommitteeMember</code>, <code>Suggestion</code>
   */
  public void deleteSuggestion(Scanner sc);
}
