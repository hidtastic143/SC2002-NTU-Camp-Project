package staff;

import java.util.Scanner;
import report.BaseReport;

/**
 * Represents the StaffReport interface. Abstract methods relating to the interactions
 * with the <code>Staff</code>.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 * @see <code>Staff</code>
 */
public interface StaffReport extends BaseReport{
  /**
   * Generates the participant report of a camp.
   * 
   * @param sc The scanner object to be injected.
   * @see <code>Enquiry</code>
   */
  public void generateParticipantReport(Scanner sc);
  
  /**
   * Generates the performace report of a camp.
   * 
   * @param sc The scanner object to be injected.
   * @see <code>Enquiry</code>
   */
  public void generatePerformanceReport(Scanner sc);
  
  /**
   * Generates the enquiry report of a camp.
   * 
   * @param sc The scanner object to be injected.
   * @see <code>Enquiry</code>
   */
  public void generateEnquiryReport(Scanner sc);
}
