package enums;

import java.util.Scanner;

/**
 * Represents the file format types.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 * @see <code>Staff</code>
 */
public enum Format {
  CSV,
  TXT;
  
  /**
   * Prints all Format enumerated values in the console.
   * 
   */
  public static void printAll() {
    Format[] formatList = Format.values();
    System.out.println("List of formats:");
    for (Format f: formatList) {
      System.out.println(f);
    }
  }
  
  /**
   * Searches against all Format enumerated values and returns the <code>Format</code> if
   * there is a match, or null is there is not.
   * 
   * @param str the string to perform the search on
   * @return the Format enum value that matches the search str
   */
  public static Format search(String str) {
    Format[] formatList = Format.values();
    for (Format f: formatList) {
      if (str.equals(f.toString())) {
        return f;
      }
    }
    return null;
  }
  
  /**
   * Gets a string input from the user, and matches it against all Format enumerated values and
   * returns the Format is there is a match, re-prompts the user to enter the input again if the
   * input is not found.
   * 
   * @param sc the scanner object to be passed in
   * @return the Format enum value that matches user input
   */
  public static Format getFormatFromStringInput(Scanner sc) {
    printAll();
    Format result = null;
    do {
      String userInput = file.Input.getStringInput("Enter your selection: ", sc);
      result = search(userInput.toUpperCase());
      if (result != null) {
        return result;
      }
      System.out.println("Your selection does not match any formats, please re-enter.");
    } while(result == null);
    return result; // Logically, this line will never be reached. Included to keep the compiler from showing error of not returning.
  }
}
