package enums;

import java.util.Scanner;

/**
 * Represents the role types.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 * @see <code>Staff</code>
 */
public enum Role {
  STAFF,
  STUDENT,
  COMMITTEE_MEMBER;

  /**
   * Prints all Role enumerated values in the console.
   * 
   */
  public static void printAll() {
    Role[] roleList = Role.values();
    System.out.println("List of faculties:");
    for (Role r: roleList) {
      System.out.println(r);
    }
  }
  
  /**
   * Searches against all Role enumerated values and returns the <code>Role</code> if
   * there is a match, or null is there is not.
   * 
   */
  public static Role search(String str) {
    Role[] roleList = Role.values();
    for (Role r: roleList) {
      if (str.equals(r.toString())) {
        return r;
      }
    }
    return null;
  }
  
  /**
   * Gets a string input from the user, and matches it against all Role enumerated values and
   * returns the Role is there is a match, re-prompts the user to enter the input again if the
   * input is not found.
   */
  public static Role getRoleFromStringInput(Scanner sc) {
    printAll();
    Role result = null;
    do {
      String userInput = file.Input.getStringInput("Enter your selection: ", sc);
      result = search(userInput.toUpperCase());
      if (result != null) {
        return result;
      }
      System.out.println("Your selection does not match any roles, please re-enter.");
    } while(result == null);
    return result; // Logically, this line will never be reached. Included to keep the compiler from showing error of not returning.
  }
  
}