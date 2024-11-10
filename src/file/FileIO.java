package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import enums.Format;

/**
 * The <code>FileIO</code> class contains various methods to provide support for reading and writing
 * to files of differrent format. Assists users of this class to avoid repetition of boilerplate
 * code by presenting it in a more concise manner (one liners), and allows injection of
 * <code>ArrayLists</code> of various classes to facilitate ease of use.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 */
public class FileIO {

  /**
   * Writes the content of an injected <code>ArrayList</code> of strings to a text file of either .csv or .txt
   * format with the provided filename.
   * 
   * @param format	 The format of the output file
   * @param fileName The name of the output file
   * @param anArray  An ArrayList of strings which holds the content for writing to the file
   * 
   */
  public static void writeToFile(Format format,String fileName, ArrayList<String> anArray) {
    String fileExtension = format.toString();
    if (fileExtension == Format.CSV.toString())
      fileExtension = ".csv";
    else
      fileExtension = ".txt";
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + fileExtension));
      for (String line : anArray) {
        writer.write(line + "\n");
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Reads the content of a text file of .csv format, and stores it into an <code>ArrayList</code>
   * of strings.
   * 
   * @param fileName The name of the file to be read.
   * @return An <code>ArrayList</code> of strings with the content
   * 
   */
  public static ArrayList<String> readFromCSV(String fileName) {
    String format = ".csv";
    ArrayList<String> resultArr = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileName + format));

      String line;
      while ((line = reader.readLine()) != null) {
        resultArr.add(line);
      }
      reader.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return resultArr;
  }

}


