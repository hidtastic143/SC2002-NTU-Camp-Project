package file;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * The <code>input</code> class contains various methods to provide support for obtaining inputs of
 * different data types from the client. Assists users of this class to avoid repetition of
 * boilerplate code by presenting it in a more concise manner (one liners), and allows injection of
 * class methods into custom setters, constructors and initialization of variables.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 */
public class Input {

  /**
   * Displays a message and returns a given integer input from the client.
   * 
   * @param message A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as an integer.
   * 
   */
  public static int getIntInput(String message, Scanner sc) {
    System.out.print(message);
    int input = sc.nextInt();
    sc.nextLine();
    return input;
  }

  /**
   * Displays a message and returns a given string input from the client.
   * 
   * @param message A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as a string.
   * 
   */
  public static String getStringInput(String message, Scanner sc) {
    System.out.print(message);
    String input = sc.nextLine();
    return input;
  }

  /**
   * Displays a message and returns a given float input from the client.
   * 
   * @param message A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as an float.
   * 
   */
  public static float getFloatInput(String message, Scanner sc) {
    System.out.print(message);
    float input = sc.nextFloat();
    return input;
  }

  /**
   * Displays a message and returns a givenn double input from the client.
   * 
   * @param message A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as a double.
   */
  public static double getDoubleInput(String message, Scanner sc) {
    System.out.print(message);
    double input = sc.nextDouble();
    return input;
  }

  /**
   * Displays a message and returns a given character input from the client.
   * 
   * @param message A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as a char.
   */
  public static char getCharInput(String message, Scanner sc) {
    System.out.print(message);
    char input = sc.next().charAt(0);
    return input;
  }

  /**
   * Displays a message and returns a given integer input from the client as a valid day of month
   * value.
   * 
   * @param message A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as an valid integer for a day in a calendar month.
   * 
   */
  public static int getDayFromIntInput(String message, Scanner sc) {
    int dayMin = 1;
    int dayMax = 31;
    boolean outOfRange = true;
    do {
      int day = getIntInput("Enter the day for " + message, sc);
      boolean betweenMinAndMax = (day >= dayMin && day <= dayMax);
      if (betweenMinAndMax)
        return day;
      else
        System.out
            .print("You have entered an invalid day value. Please re-enter. Valid values: (1-31).");
    } while (outOfRange);
    return -1;
  }

  /**
   * Displays a message and returns a given integer input from the client as a valid month of year
   * value.
   * 
   * @param message A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as an valid integer for a month in a calendar year.
   * 
   */
  public static int getMonthFromIntInput(String message, Scanner sc) {
    int monthMin = 1;
    int monthMax = 12;
    boolean outOfRange = true;
    do {
      int month = getIntInput("Enter the month for " + message, sc);
      boolean betweenMinAndMax = (month >= monthMin && month <= monthMax);
      if (betweenMinAndMax)
        return month;
      else
        System.out.print(
            "You have entered an invalid month value. Please re-enter. Valid values: (1-12).");
    } while (outOfRange);
    return -1;
  }

  /**
   * Displays a message and returns a given integer input from the client as a valid day of month
   * value. <br>
   * 
   * @param message A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as an valid integer for a year in a YYYY format.
   */
  public static int getYearFromIntInput(String message, Scanner sc) {
    int yearMin = 0;
    int yearMax = 9999;
    boolean outOfRange = true;
    do {
      int year = getIntInput("Enter the year for " + message, sc);
      boolean betweenMinAndMax = (year >= yearMin && year <= yearMax);
      if (betweenMinAndMax)
        return year;
      else
        System.out.print(
            "You have entered an invalid year value. Please re-enter. Valid values: (0000-9999).");
    } while (outOfRange);
    return -1;
  }

  /**
   * Displays a message and returns a <code>LocalDate</code> from three integer inputs from the
   * client as a valid date.
   * 
   * @param message A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as an valid <code>LocalDate</code>.
   */
  public static LocalDate getDateFromIntInputs(String message, Scanner sc) {
    int day = getDayFromIntInput(message, sc);
    int month = getMonthFromIntInput(message, sc);
    int year = getYearFromIntInput(message, sc);
    return LocalDate.of(year, month, day);
  }

}
