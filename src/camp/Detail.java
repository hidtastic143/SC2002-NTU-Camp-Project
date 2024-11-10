package camp;

import java.time.LocalDate;
import java.util.Scanner;
import enums.Faculty;
import staff.Staff;

/**
 * Represents details of a <code>camp</code>. Can only be modified by a <code>Staff</code>. Created
 * at the same time when a <code>camp</code> is created.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 * @see <code>Staff</code>, <code>Camp</code>
 */
public class Detail {
  /**
   * Represents the staff in charge of this camp. The staff in charge is also the creator of this
   * camp.
   * 
   * @see <code>Staff</code>
   */
  private Staff staffInCharge;
  /**
   * Represents the name of the staff in charge of this camp.
   */
  private String staffInChargeName;
  /**
   * Represents the name of this camp.
   */
  private String name;
  /**
   * Represents the location of this camp.
   */
  private String location;
  /**
   * Represents the description of this camp.
   */
  private String description;
  /**
   * Represents the faculty of this camp. Uses the <code>Faculty</code> enum class.
   */
  private Faculty faculty;
  /**
   * Represents the registration deadline of this camp. Uses the built-in <code>LocalDate</code>
   * class from the <code>java.time</code> package.
   * 
   * @see <code>LocalDate</code>, <code>java.time</code>
   */
  private LocalDate registrationClosingDate;
  /**
   * Represents the starting date of this camp. Uses the built-in <code>LocalDate</code> class from
   * the <code>java.time</code> package.
   * 
   * @see <code>LocalDate</code>, <code>java.time</code>
   */
  private LocalDate startDate;
  /**
   * Represents the ending date of this camp. Uses the built-in <code>LocalDate</code> class from
   * the <code>java.time</code> package.
   * 
   * @see <code>LocalDate</code>, <code>java.time</code>
   */
  private LocalDate endDate;
  /**
   * Represents the number of slots for participants of this camp.
   * 
   * @see <code>Student</code>, <code>CommitteeMember</code>
   */
  private int totalSlots;
  /**
   * Represents the number of slots for committee members of this camp.
   * 
   * @see <code>CommitteeMember</code>
   */
  private int committeeSlots;
  /**
   * Represents the maximum number of slots for committee members of this camp. Set to a value of 10
   * as per requirements.
   * 
   * @see <code>CommitteeMember</code>
   */
  private final int COMMITTEE_SLOTS_CAPACITY = 10;

  // default methods, Javadoc skipped as methods are self-explanatory
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Faculty getFaculty() {
    return faculty;
  }

  public void setFaculty(Faculty faculty) {
    this.faculty = faculty;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalDate getRegistrationClosingDate() {
    return registrationClosingDate;
  }

  public void setRegistrationClosingDate(LocalDate registrationClosingDate) {
    this.registrationClosingDate = registrationClosingDate;
  }

  public int getTotalSlots() {
    return totalSlots;
  }

  public void setTotalSlots(int totalSlots) {
    this.totalSlots = totalSlots;
  }

  public int getCommitteeSlots() {
    return committeeSlots;
  }

  public void setCommitteeSlots(int committeeSlots) {
    this.committeeSlots = committeeSlots;
  }

  public Staff getStaffInCharge() {
    return staffInCharge;
  }

  public String getStaffInChargeName() {
    return staffInChargeName;
  }

  public int getCOMMITTEE_SLOTS_CAPACITY() {
    return COMMITTEE_SLOTS_CAPACITY;
  }

  // non-default methods, to be reflected in UML

  public Detail(Staff staff)
  {
	  staffInCharge = staff;
	  staffInChargeName = staff.getName();
	  name = staff.getFaculty().toString() + " Camp";
	  location = staff.getFaculty().toString() + " Building";
	  description = "Post Examination Camp for " + staff.getFaculty().toString();
	  faculty = staff.getFaculty();
	  startDate = LocalDate.now().plusDays(5);
	  registrationClosingDate = LocalDate.now().plusDays(3);;
	  endDate = LocalDate.now().plusDays(10);
	  
	  totalSlots = 20;
	  committeeSlots = 10;
  }
  
  /**
   * Custom constructor for a Detail object.
   * 
   * @param sc the scanner object to be injected
   * @param aStaff the staff object to be injected
   * 
   * @see <code>Detail</code>
   */
  public Detail(Scanner sc, Staff aStaff) {
    staffInCharge = aStaff;
    staffInChargeName = aStaff.getName();
    name = file.Input.getStringInput("Enter the name of this camp: ", sc);
    location = file.Input.getStringInput("Enter the location of this camp: ", sc);
    description = file.Input.getStringInput("Enter the description of this camp: ", sc);
    faculty = Faculty.getFacultyFromStringInput(sc);
    startDate = file.Input.getDateFromIntInputs("start date: ", sc);

    LocalDate registrationclosingdate;
    do {
      registrationclosingdate = file.Input.getDateFromIntInputs("registration deadline: ", sc);
      if (registrationclosingdate.isAfter(startDate))
        System.out
            .println("Registration closing date cannot be later than start date. Please re-enter.");
      else
        this.registrationClosingDate = registrationclosingdate;
    } while (registrationclosingdate.isAfter(startDate));

    LocalDate endDate;
    do {
      endDate = file.Input.getDateFromIntInputs("end date: ", sc);
      if (endDate.isBefore(startDate))
        System.out.println("End date cannot be earlier than start date. Please re-enter.");
      else
        this.endDate = endDate;
    } while (endDate.isBefore(startDate));

    this.totalSlots = file.Input.getIntInput("Enter the combined total slots for this camp: ", sc);

    int committeeSlots;
    do {
      committeeSlots =
          file.Input.getIntInput("Enter the total committee slots for this camp: ", sc);
      if (committeeSlots > COMMITTEE_SLOTS_CAPACITY)
        System.out.println("Committee member slots cannot be higher than the maximum capacity of "
            + COMMITTEE_SLOTS_CAPACITY + ". Please re-enter.");
      else
        this.committeeSlots = committeeSlots;
    } while (committeeSlots > COMMITTEE_SLOTS_CAPACITY);
  }

  /**
   * Overriden toString method for <code>Detail</code> class. Generates and returns the attribute
   * values as a string with the " | " character as a delimiter.
   * 
   * @return string result of all of this detail's attribute values with the exception of
   *         <code> StaffInCharge </code>
   */
  @Override
  public String toString() {
    String delimiter = " | ";
    return this.staffInChargeName + delimiter + this.name + delimiter + this.location + delimiter
        + this.description + delimiter + this.faculty + delimiter + this.startDate + delimiter
        + this.endDate + delimiter + this.registrationClosingDate + delimiter + this.totalSlots
        + delimiter + this.committeeSlots;
  }

  /**
   * Generates and returns the attribute names for use as CSV headers as a string with the ","
   * character as a delimiter.
   * 
   * @return string result of all of this detail's attribute names.
   */
  public static String generateCSVHeaders() {
    String delimiter = ", ";
    return "staffInChargeName" + delimiter + "name" + delimiter + "location" + delimiter
        + "description" + delimiter + "faculty" + delimiter + "registrationClosingDate" + delimiter
        + "endDate" + delimiter + "startDate" + delimiter + "totalSlots" + delimiter
        + "committeeSlots";
  }

  /**
   * Generates and returns the attribute values in CSV format as a string with the "," character as
   * a delimiter.
   * 
   * @return string result of all of this detail's attribute values with the exception of
   *         <code> StaffInCharge </code>
   */
  public String toCSV() {
    String delimiter = ", ";
    return this.staffInChargeName + delimiter + this.name + delimiter + this.location + delimiter
        + this.description + delimiter + this.faculty + delimiter + this.startDate + delimiter
        + this.endDate + delimiter + this.registrationClosingDate + delimiter + this.totalSlots
        + delimiter + this.committeeSlots;
  }

  /**
   * Prints a <code>Camp</code> object in a format suitable for CLI. Displays all attributes except
   * <code>StaffInCharge</code>.
   */
  public void print() {
    String delimiter = "-";
    String paddingParameters =
        "| %-20s | %-20s | %-20s | %-40s | %-10s | %-25s | %-25s | %-25s | %-15s | \n";
    System.out.printf(paddingParameters, this.getStaffInChargeName(), this.getName(),
        this.getLocation(), this.getDescription(), this.getFaculty(),
        this.getRegistrationClosingDate().getDayOfMonth() + delimiter
            + this.getRegistrationClosingDate().getMonthValue() + delimiter
            + this.getRegistrationClosingDate().getYear(),
        this.getStartDate().getDayOfMonth() + delimiter + this.getStartDate().getMonthValue()
            + delimiter + this.getStartDate().getYear(),
        this.getEndDate().getDayOfMonth() + delimiter + this.getEndDate().getMonthValue()
            + delimiter + this.getEndDate().getYear(),
        this.getTotalSlots());
  }

}
