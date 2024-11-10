package application;

import enums.Role;
import file.FileIO;
import user.User;
import staff.Staff;
import student.Student;
import enums.Faculty;
import java.util.*;

/**@author Chen yan Jin 
@version 3.0 */

public class UserManager {
    
    // List to store user objects
    private HashMap<String, User> studentMap;
    private HashMap<String, Staff> staffMap;

    // Constructor to initialize the list of users
    public UserManager() {
    	studentMap = new HashMap<String, User>();
    	staffMap = new HashMap<String, Staff>();
        
    	initialise();
    }

    // Example method to initialize users based on the provided text information
    /* public static List<User> initializeUsersFromText(String staffListText, String studentListText) {
        List<User> users = new ArrayList<>();

        // Initialize staff users
        users.addAll(readUsersFromText(staffListText, Role.STAFF));

        // Initialize student users
        users.addAll(readUsersFromText(studentListText, Role.STUDENT));

        return users;
    }

    // Helper method to read users from text and set their properties
    /*private static List<User> readUsersFromText(String text, Role defaultRole) {
        List<User> users = new ArrayList<>();

        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] tokens = line.trim().split("\t");
            if (tokens.length >= 3) {
                String name = tokens[0];
                String email = tokens[1];
                String facultyString = tokens[2];
                
                try {
                    // Parse the faculty value from the string
                    Faculty faculty = Faculty.valueOf(facultyString);

                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setFaculty(faculty);

                    // Set the userID directly from the email
                    int atIndex = email.indexOf('@'); // Find the position of '@' in the email
                    if (atIndex != -1) {
                        user.setUserID(email.substring(0, atIndex));
                    } else {
                        user.setUserID(email);
                    }

                    // Create a mutable set for roles
                    Set<Role> roles = new HashSet<>();
                    roles.add(defaultRole);
                    user.setRoles(roles);

                    users.add(user);
                } catch (IllegalArgumentException e) {
                    // Handle the case where facultyString is not a valid enum constant
                    // Log a warning or handle it as needed
                    System.out.println("Invalid faculty: " + facultyString);
                }
            }
        }

        return users;
    }*/

    // Method to get a user by their userID
    public User getStudentByID(String userID) {
        if (studentMap.containsKey(userID))
        	return studentMap.get(userID);
        else
        	return null;
    }
    
    public Staff getStaffByID(String userID) {
        if (staffMap.containsKey(userID))
        	return staffMap.get(userID);
        else
        	return null;
    }

    // Method to get the list of users
    public HashMap<String, User> getStudentList() {
        return studentMap;
    }
    
    public HashMap<String, Staff> getStaffList() {
        return staffMap;
    }
    
    // Method to add a user to the list
    public void addUser(User user) {
       studentMap.put(user.getUserID(), user);
    }
    
    public void addStaff(Staff staff) {
    	staffMap.put(staff.getUserID(), staff);
    }
    
    public void removeUser(User user)
    {
    	studentMap.remove(user.getUserID());
    }
    
    void initialise()
    {
    	ArrayList<String> studentList = FileIO.readFromCSV("studentlist");
    	ArrayList<String> staffList = FileIO.readFromCSV("stafflist");
    	
    	// TODO Insert each arraylist into HashMap<String, User / Staff>
    	for(int index = 1; index < studentList.size(); index++)
    	{
    		String[] stdDetails = studentList.get(index).split(",");
    		Faculty faculty = Faculty.search(stdDetails[4]);
    		// [0] UserID, [1] password, [2] name, [3] email [4] Faculty
    		Student std = new Student(stdDetails[0], stdDetails[1], Role.STUDENT, stdDetails[2], stdDetails[3], faculty);
    		studentMap.put(stdDetails[0], std);
    	}
    	
    	for(int index = 1; index < staffList.size(); index++)
    	{
    		String[] staffDetails = staffList.get(index).split(",");
    		Faculty faculty = Faculty.search(staffDetails[4]);
    		// [0] UserID, [1] password, [2] name, [3] email [4] Faculty
    		Staff staff = new Staff(staffDetails[0], staffDetails[1], Role.STAFF, staffDetails[2], staffDetails[3], faculty);
    		staffMap.put(staffDetails[0], staff);
    	}
    	
    }
}
