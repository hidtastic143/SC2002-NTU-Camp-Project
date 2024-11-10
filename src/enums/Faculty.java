package enums;

import java.util.Scanner;

/**
 * Represents the Faculty types.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 * @see <code>Staff</code>
 */
public enum Faculty {
  SCSE,
  ADM,
  EEE,
  NBS,
  SSS,
  NTU;

  /**
   * Prints all Faculty enumerated values in the console.
   * 
   */
  public static void printAll() {
    Faculty[] facultyList = Faculty.values();
    System.out.println("List of faculties:");
    for (Faculty f: facultyList) {
      System.out.println(f);
    }
  }
  
  /**
   * Searches against all Faculty enumerated values and returns the <code>Faculty</code> if
   * there is a match, or null is there is not.
   * 
   * @param str the string to perform the search on
   * @return the Faculty enum value that matches the str passed in
   */
  public static Faculty search(String str) {
    Faculty[] facultyList = Faculty.values();
    for (Faculty f: facultyList) {
      if (str.equals(f.toString())) {
        return f;
      }
    }
    return null;
  }
  
  /**
   * Gets a string input from the user, and matches it against all Faculty enumerated values and
   * returns the Faculty is there is a match, re-prompts the user to enter the input again if the
   * input is not found.
   * 
   * @param sc the scanner object to be injected
   * @return the Faculty enum values that matches user input
   */
  public static Faculty getFacultyFromStringInput(Scanner sc) {
    printAll();
    Faculty result = null;
    do {
      String userInput = file.Input.getStringInput("Enter your selection: ", sc);
      result = search(userInput.toUpperCase());
      if (result != null) {
        return result;
      }
      System.out.println("Your selection does not match any faculties, please re-enter.");
    } while(result == null);
    return result; // Logically, this line will never be reached. Included to keep the compiler from showing error of not returning.
  }
  
}
