package application;

import java.util.Scanner;

import user.User;
/**@author Chen yan Jin
 * 
 */

public class SecurityManager 
{	
	 /**
	 * Authenticates a user based on the provided username and password.
	 * If authentication is successful, the corresponding User object is returned.
	 * If the user does not exist or the password is incorrect, it returns null.
	 * If the password is the default, the user is prompted to change it.
	 *
	 * @param scan	Scanner object for user input
	 * @param userManager	UserManager object managing user data
	 * 
	 * @return User object if authentication is successful, otherwise null
	 */
	public User logInAuthentication(Scanner scan, UserManager userManager)
	{
		User user;
		String userID, password = "";
		
        System.out.println("Enter your Username and Password");
		System.out.print("username : ");
		userID = scan.nextLine();
		System.out.print("password : ");
		password = scan.nextLine();
		
		
		// If UserID cannot be found in any Map
		if (userManager.getStudentByID(userID) == null && userManager.getStaffByID(userID) == null)
		{
			System.out.println("User do not exist");
			return null;
		}
		
		// Look Through Student Map and see if password is incorrect
		if (userManager.getStudentByID(userID) != null)
		{
			user = userManager.getStudentByID(userID);
			if (!user.getPassword().equals(password))
			{
				System.out.println("Incorrect Password!");
				return null;
			}
		}
		// Look Through Staff Map and see if password is incorrect
		else
		{
			user = userManager.getStaffByID(userID);
			if (!user.getPassword().equals(password))
			{
				System.out.println("Incorrect Password!");
				return null;
			}
		}
		
		// If password is default, prompt user to change
		if (password.equals("password"))
			changePassword(user, scan, userManager);
		System.out.println();
		return user;
	}
	
	
	
	
    /**
     * Changes the password for the specified user.
     * The user is prompted to enter a new password, and the password is updated.
     * After changing the password, the user is required to log in again with the new password.
     *
     * @param user         User object for which the password needs to be changed
     * @param scan         Scanner object for user input
     * @param userManager  UserManager object managing user data
     */
	
	public void changePassword(User user, Scanner scan, UserManager userManager)
	{
		System.out.println("New Log In detected");
		System.out.print("Please Enter New Password : ");
		
		String password = scan.nextLine();
		user.setPassword(password);
		
		System.out.println("Password Sucessfully Changed");
		System.out.println("Kindly re enter your username with new password");
		System.out.println();
		
		do {
			user = logInAuthentication(scan, userManager);
		} while(user == null);
		
		System.out.println();
	}
}
