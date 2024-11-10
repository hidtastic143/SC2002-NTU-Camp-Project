package enums;

import java.util.Scanner;

/**
 * Represents the role filter types. Used in conjunction with the <code>Staff</code> class for
 * report generation.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 * @see <code>Staff</code>
 */
public enum RoleFilter {
  STUDENT,
  COMMITTEE_MEMBER;
  
  /**
   * Prints all RoleFilter enumerated values in the console.
   * 
   */
  public static void printAll() {
    RoleFilter[] roleFilterList = RoleFilter.values();
    System.out.println("List of filters:");
    for (RoleFilter rf: roleFilterList) {
      System.out.println(rf);
    }
  }
  
  /**
   * Searches against all RoleFilter enumerated values and returns the <code>RoleFilter</code> if
   * there is a match, or null is there is not.
   * 
   */
  public static RoleFilter search(String str) {
    RoleFilter[] roleFilterList = RoleFilter.values();
    for (RoleFilter rf: roleFilterList) {
      if (str.equals(rf.toString())) {
        return rf;
      }
    }
    return null;
  }
  
  /**
   * Gets a string input from the user, and matches it against all RoleFilter enumerated values and
   * returns the RoleFilter is there is a match, re-prompts the user to enter the input again if the
   * input is not found.
   */
  public static RoleFilter getRoleFilterFromStringInput(Scanner sc) {
    printAll();
    RoleFilter result = null;
    do {
      String userInput = file.Input.getStringInput("Enter your selection: ", sc);
      result = search(userInput.toUpperCase());
      if (result != null) {
        return result;
      }
      System.out.println("Your selection does not match any filters, please re-enter.");
    } while(result == null);
    return result; // Logically, this line will never be reached. Included to keep the compiler from showing error of not returning.
  }
}
