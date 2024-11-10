package enums;

import java.util.Scanner;

/**
 * Represents the date filter types. Used in conjunction with the <code>Staff</code> class for
 * report generation.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 * @see <code>Staff</code>
 */
public enum DateFilter {
  ON, BEFORE, AFTER;

  /**
   * Prints all DateFilter enumerated values in the console.
   * 
   */
  public static void printAll() {
    DateFilter[] dateFilterList = DateFilter.values();
    System.out.println("List of filters:");
    for (DateFilter df : dateFilterList) {
      System.out.println(df);
    }
  }

  /**
   * Searches against all DateFilter enumerated values and returns the <code>DateFilter</code> if
   * there is a match, or null is there is not.
   * @param str the string to perform search on
   * @return a DateFilter enum value that matches the search str
   */
  public static DateFilter search(String str) {
    DateFilter[] dateFilterList = DateFilter.values();
    for (DateFilter df : dateFilterList) {
      if (str.equals(df.toString())) {
        return df;
      }
    }
    return null;
  }

  /**
   * Gets a string input from the user, and matches it against all DateFilter enumerated values and
   * returns the DateFilter is there is a match, re-prompts the user to enter the input again if the
   * input is not found.
   * 
   *  @param sc the scanner object to be injected
   *  @return a DateFilter enum value that matches the search str obtained from the user
   */
  public static DateFilter getDateFilterFromStringInput(Scanner sc) {
    printAll();
    DateFilter result = null;
    do {
      String userInput = file.Input.getStringInput("Enter your selection: ", sc);
      result = search(userInput.toUpperCase());
      if (result != null) {
        return result;
      }
      System.out.println("Your selection does not match any filters, please re-enter.");
    } while (result == null);
    return result; // Logically, this line will never be reached. Included to keep the compiler from
                   // showing error of not returning.
  }
}
