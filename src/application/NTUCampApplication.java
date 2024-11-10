package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import camp.Camp;
import committeeMember.CommitteeMember;
import enquiry.Enquiry;
import enums.Role;
import user.User;
import file.FileIO;
import staff.Staff;
import student.Student;

/**
 * Main application to run the NTU Camp Management. 
 * 
 * @author Nurhidayat
 * @version 1.0
 */
public class NTUCampApplication 
{	
	/**
	 * Scanner variable "scan" to receive user input.
	 */
	static Scanner scan;
	/**
	 * SecurityManager variable "authenticator" to handle user logins and change password methods
	 */
	static SecurityManager authenticator;
	/**
	 * UserManager variable "userManager" to handle users within the school 
	 */
	static UserManager userManager;
	/**
	 * MenuStates variable "menu" to tell the programme which state of the menu it is in
	 */
	static MenuStates menu;
	/**
	 * MenuStates variable "prevMenu" to store last visited menu so there is a way to go back to previous menu
	 */
	static MenuStates prevMenu;
	/**
	 * User variable "user" to indicate the current logged in user
	 */
	static User user;
	/**
	 * ArrayList variable storing camps of NTU
	 */
	static ArrayList<Camp> campList;
	
	/**
	 * Main Function for NTU Camp Application
	 * @param args Default main method
	 */
	public static void main(String[] args) 
	{
		// Initialise variables through this method
		initialise();
		
		while (menu != MenuStates.EXIT)
		{
			System.out.println();
			switch (menu)
			{
			case PRELOG_IN:
				preLogInMenu();
				break;
			case LOG_IN:
				logInMenu();
				break;
			case PRELOG_OUT:
				preLogOutMenu();
				break;
			case LOG_OUT:
				logOutMenu();
				break;
			case PROFILE:
				viewProfileMenu();
				break;
			case VIEW_CAMPS:
				viewCampsMenu();
				break;
			case CAMP_DETAILS:
				viewCampDetailsMenu();
				break;
			case VIEW_ENQUIRIES:
				viewEnquiriesMenu();
				break;
			case REPORT_PARTICIPANT:
				generateReportParticipantsMenu();
				break;
			case REPORT_ENQUIRY:
				generateReportEnquiryMenu();
				break;
			case STUDENT_MAIN_MENU:
				studentMainMenu();
				break;
			case SUBMIT_ENQUIRY:
				submitEnquiryMenu();
				break;
			case CM_MAIN_MENU:
				committeeMainMenu();
				break;
			case SUBMIT_SUGGESTION:
				submitSuggestionMenu();
				break;
			case STAFF_MAIN_MENU:
				staffMainMenu();
				break;
			case EDIT_CAMP:
				editCampMenu();
				break;
			case CREATE_CAMP:
				createCampMenu();
				break;
			case DELETE_CAMP:
				deleteCampMenu();
				break;
			case VIEW_SUGGESTIONS:
				viewSuggestionsMenu();
				break;
			case REPORT_PERFORMANCE:
				generateReportPerformanceMenu();
				break;
			default:
				System.out.println("ERROR, MENU DONT EXIST");
				break;
			}
		}
		System.out.println("END PROGRAMME");
	}
	
	/**
	 * This method changes the current menu state to the desired menu state according to user choice.
	 * Also, it will store the previous menu state in variable "prevMenu"
	 * @param newState; Next menu the programme will display
	 */
	static void setMenuState(MenuStates newState)
	{
		prevMenu = menu;
		menu = newState;
	}
	
	/**
	 * This method is called whenever user chooses an integer outside of the scope of menu number
	 */
	static void errorChoice()
	{
		System.out.println("Invalid Choice! Choose Again");
	}
	
	/**
	 * This method activates all the variable required to run the programme
	 */
	static void initialise()
	{
		scan = new Scanner(System.in);
		authenticator = new SecurityManager();
		userManager = new UserManager();
		campList = new ArrayList<Camp>();
		prevMenu = menu = MenuStates.PRELOG_IN;

		// Uncomment / comment to show demo/hide
		demo();
	}
	
	/**
	 * This function was created for the sole purpose of creating camps and have students join ahead of time for demo.
	 * Just like a script! Kinda..
	 */
	static void demo()
	{
		HashMap<String, User> studentList = userManager.getStudentList();
		HashMap<String, Staff> staffList = userManager.getStaffList();
		
		for (Map.Entry<String, Staff> map : staffList.entrySet())
		{
			if (!map.getValue().getPassword().equals("password") == false);
			{
				Camp camp = new Camp(map.getValue());
				map.getValue().setCreatedCamp(camp);
				campList.add(camp);
			}
		}
		
		for (Map.Entry<String, User> map : studentList.entrySet())
		{
			if (map.getValue().getPassword().equals("password") == false)
			{
				for (Camp camp : campList)
				{
					if (camp.getDetails().getFaculty().equals(map.getValue().getFaculty()))
					{
						camp.addParticipant((Student)map.getValue());
						((Student)map.getValue()).register(camp);
					}
				}
			}
		}
		
		/*for (Camp camp : campList)
		{
			System.out.println(camp.getDetails().getName() + " created");
			System.out.println(camp.getDetails().getStaffInChargeName());
			System.out.println(camp.getParticipants());
		}*/
	}
	
	// Application Menus ///////////////////////
	
	/**
	 * The first menu that user is greeted by to choose to log in or exit programme
	 */
	static void preLogInMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|         NTU CAMP APPLICATION        |");
        System.out.println("---------------------------------------");
        System.out.println("|            1. Login                 |");
        System.out.println("|            2. Exit                  |");
        System.out.println("---------------------------------------");

        System.out.print("Enter your choice: ");
        int choice = scan.nextInt();
        scan.nextLine();
        
        if (choice == 1)
        	setMenuState(MenuStates.LOG_IN);
        else if (choice == 2)
        	setMenuState(MenuStates.EXIT);
        else
        	errorChoice();
	}
	
	/**
	 * This is the log in menu that will require User to log in. "authenticator" variable will be called to verify if user exists in the "userManager" variable.
	 */
	static void logInMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|           NTU CAMP LOG IN           |");
        System.out.println("---------------------------------------");

		
		user = authenticator.logInAuthentication(scan,userManager);
		
		if (user == null)
			return;
		
		if (user.getRole() == Role.STUDENT)
			setMenuState(MenuStates.STUDENT_MAIN_MENU);
		else if (user.getRole() == Role.COMMITTEE_MEMBER)
			setMenuState(MenuStates.CM_MAIN_MENU);
		else
			setMenuState(MenuStates.STAFF_MAIN_MENU);
	}
	
	/**
	 * This menu requires user to confirm his choice to log out
	 */
	static void preLogOutMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|         CONFIRM LOG OUT?            |");
        System.out.println("---------------------------------------");
        System.out.println("|            1. Logout                |");
        System.out.println("|            2. Back                  |");
        System.out.println("---------------------------------------");
        System.out.print("Please pick a menu: ");
        int choice = scan.nextInt();
        
        if (choice == 1)
        	setMenuState(MenuStates.LOG_OUT);
        else if (choice == 2)
        	setMenuState(prevMenu);
        else
        	errorChoice();
    }
	
	/**
	 * This method just prints out log out confirmation and sets menu to PRELOG_IN
	 */
	static void logOutMenu()
	{
		System.out.println("Sucessfully Logged Out! Thank you " + user.getName());
		setMenuState(MenuStates.PRELOG_IN);
	}
	
	/**
	 * This menu prints out the main details of the user and has an option to change password
	 */
	static void viewProfileMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|           YOUR PROFILE              |");
        System.out.println("---------------------------------------");
        System.out.println("|1. Change Password                   |");
        System.out.println("|2. Back                              |");
        System.out.println("---------------------------------------");
        
        System.out.println("Name : " + user.getName());
        System.out.println("UserID : " + user.getUserID());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Faculty : " + user.getFaculty().toString());
        System.out.println("Role : " + user.getRole().toString());
        System.out.println();
        
        System.out.print("Please pick a choice: ");
        int choice = scan.nextInt();
        scan.nextLine();
        
        switch(choice)
        {
        case 1:
        	System.out.print("Type in your new password: ");
    		user.setPassword(scan.nextLine());
    		break;
        case 2:
        	break;
        default:
        	errorChoice();
        	System.out.println("Returning to prev menu..");
        	break;
        }
        setMenuState(prevMenu);
	}
	
	// Shared Menu Throughout All Roles ///////////////////////
	
	/**
	 * This menu is a camp menu that is shared throughout the 3 roles
	 * 
	 * STUDENT and COMMITTEE MEMBER are rather similar, the difference would be the option to sign up as Committee Member
	 * STAFF will print detailed information of all camps in the NTU Application
	 * 
	 * @see Student class for methods implemented here
	 * @see CommitteeMember class for methods implemented here
	 */
	static void viewCampsMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|              ALL CAMPS              |");
        System.out.println("---------------------------------------");
        int choice;
        if (campList.size() == 0)
        {
        	System.out.println("There are no camps");
            System.out.print("Enter 1 to return back to Menu : ");
            while(scan.nextInt() != 1) {scan.nextLine();}
            setMenuState(prevMenu);
        }
        else
        {
        	if (user.getRole() == Role.STUDENT || user.getRole() == Role.COMMITTEE_MEMBER)
        	{
        		System.out.println("| 1. Join Camp                        |");
        		System.out.println("| 2. See Registered Camps             |");
        		System.out.println("| 3. Withdraw Camp                    |");
        		if (user.getRole() == Role.STUDENT)
        		{
        			System.out.println("| 4. Sign up as Committee Member      |");
        			System.out.println("| 5. Back                             |");
        		}
        		else
        		{
        			System.out.println("| 4. Back                             |");
        		}
        		System.out.println("---------------------------------------");
        		System.out.print("Please pick a menu: ");
        		
        		choice = scan.nextInt();
        		scan.nextLine();
        		
        		if (user.getRole() == Role.STUDENT) {
        			switch (choice)
            		{
            		case 1:
            		{
            			((Student)user).viewAvailableCampsToJoin(campList);
            			((Student)user).register(scan, campList);
            			setMenuState(prevMenu);
            			break;
            		}
            		case 2:
            		{
            			((Student)user).viewRegisteredCamps();
            			setMenuState(prevMenu);
            			break;
            		}
            		case 3:
            		{
            			((Student)user).withdraw(scan);
            			setMenuState(prevMenu);
            			break;
            		}
            		case 4:
            		{
            			CommitteeMember cm = ((Student)user).registerAsCM(scan, campList, (Student)user); 
            			if (cm == null)
            			{
            				System.out.println("Returning to previous menu");
            				setMenuState(prevMenu);
            			}
            			else
            			{
            				userManager.removeUser(user);
            				
            				// Change current student to become CM
            				user = cm;
            				userManager.addUser(user);
            				setMenuState(MenuStates.CM_MAIN_MENU);
            			}
            			break;
            		}
            		case 5:
            			setMenuState(prevMenu);
            			break;
            		}
        		}
        		else
        		{
        			switch (choice)
            		{
            		case 1:
            		{
            			((Student)user).register(scan, campList);
            			setMenuState(prevMenu);
            			break;
            		}
            		case 2:
            		{
            			((Student)user).viewRegisteredCamps();
            			setMenuState(prevMenu);
            		}
            		case 3:
            		{
            			((CommitteeMember)user).withdraw(scan);
            			setMenuState(prevMenu);
            			break;
            		}
            		case 4:
            		{
            			setMenuState(prevMenu);
            			break;
            		}
            		}
        		}
        		
        	}
        	else
        	{
        		for (Camp c : campList)
        			c.detailedPrint();
        		System.out.print("Enter 1 to return back to menu : ");
        		
        		do
        		{
        			choice = scan.nextInt();
        			scan.nextLine();
        			if (choice != 1)
        				errorChoice();
        		}
        		while (choice != 1);
        		setMenuState(prevMenu);
        	}
        }
		
	}
	
	/**
	 * This menu prints every detail in a camp that Committee Member or Staff is in
	 * 
	 * For Student, it will print the camps they have joined
	 * 
	 * @see <code>Student</code> for <code>getRegisteredFor()</code> method
	 * @see <code>Details</code> for <code>detailedPrint()</code> method
	 */
	static void viewCampDetailsMenu()//Camp camp)
	{
		System.out.println("---------------------------------------");
        System.out.println("|             CAMP DETAILS            |");
        System.out.println("---------------------------------------");
        
        int choice;

        if (user.getRole() == Role.STUDENT)
        {
        	System.out.println("| 1. Back                             |");
            System.out.println("---------------------------------------");
        	ArrayList<Camp> camps = ((Student)user).getRegisteredFor();
        	System.out.print("Pick a menu : ");
        	
        	for (Camp c : camps)
        		c.print();
        	
        	choice = scan.nextInt();
        	if (choice == 1)
        		setMenuState(prevMenu);
        } 
        else if (user.getRole() == Role.COMMITTEE_MEMBER)
        {
        	System.out.println("| 1. Back                             |");
            System.out.println("---------------------------------------");
        	((CommitteeMember)user).getOverseeingCamp().detailedPrint();
        	System.out.print("Pick a menu : ");
        	
        	choice = scan.nextInt();
        	if (choice == 1)
        		setMenuState(prevMenu);
        	
        }
        else // Staff Logics
        {
            System.out.println("| 1. Edit Camp                        |");
            System.out.println("| 2. Back                             |");
            System.out.println("---------------------------------------");
            ((Staff)user).getCreatedCamp().detailedPrint();
            
            System.out.print("Pick a menu : ");
            choice = scan.nextInt();
            
            switch (choice)
            {
            case 1:
            	setMenuState(MenuStates.EDIT_CAMP);
            	break;
            case 2:
            	setMenuState(prevMenu);
            	break;
            default:
            	errorChoice();
            	break;
            }
        } 
        scan.nextLine();
        System.out.println();
	}
	
	/**
	 * This menu handles all kind of enquiry options for all 3 classes
	 * 
	 * @see Student class to refer implementation, Student can create and edit enquiry  
	 * @see CommitteeMember class to refer implementation, Committee Member can create, edit and reply enquiry
	 * @see Staff class to refer implementation, Staff can reply enquiry
	 */
	static void viewEnquiriesMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|             ENQUIRY MENU            |");
        System.out.println("---------------------------------------");
		
        int choice;
        
		if (user.getRole() == Role.STUDENT)
		{
			System.out.println("| 1. Create Enquiry                   |");
			System.out.println("| 2. Edit Enquiry                     |");
			System.out.println("| 3. Delete Enquiry                   |");
			System.out.println("| 4. Back                             |");
			System.out.println("---------------------------------------");
			((Student)user).viewEnquiries();
			
			System.out.println();
			System.out.print("Pick a menu: ");
			choice = scan.nextInt();
			scan.nextLine();
			
			switch (choice)
			{
			case 1:
			{
				((Student)user).submitEnquiry(scan, campList);
				break;
			}
			case 2:
			{
				((Student)user).editEnquiry(scan);
				break;
			}
			case 3:
				((Student)user).deleteEnquiry(scan, campList);
			case 4:
				setMenuState(prevMenu);
				break;
			}
		}
		else if (user.getRole() == Role.COMMITTEE_MEMBER)
		{
			System.out.println("| 1. Create Enquiry                   |");
			System.out.println("| 2. Edit Enquiry                     |");
			System.out.println("| 3. Delete Enquiry                   |");
			System.out.println("| 4. Reply Enquiry                    |");
			System.out.println("| 5. Back                             |");
			System.out.println("---------------------------------------");
			
			((CommitteeMember)user).viewEnquiries();
			System.out.println();
			
			ArrayList<Enquiry> list = ((CommitteeMember)user).getOverseeingCamp().getEnquiries();
			System.out.println("Your Camp Enquiries");
			System.out.println("---------------------------------------");
			for (Enquiry enquiry : list)
			{
				System.out.println(enquiry.getEnquirerName() + ", " + enquiry.getContents());
			}
			
			System.out.print("Pick a menu: ");
			choice = scan.nextInt();
			scan.nextLine();
			
			switch(choice)
			{
			case 1:
				((Student)user).submitEnquiry(scan, campList);
				break;
			case 2:
				((Student)user).editEnquiry(scan);
				break;
			case 3:
				((Student)user).deleteEnquiry(scan, campList);
				break;
			case 4:
				((CommitteeMember)user).replyEnquiry(scan);
				break;
			case 5:
				setMenuState(prevMenu);
				break;
			}
		}
		else
		{
			System.out.println("| 1. Reply Enquiry                    |");
			System.out.println("| 2. Back                             |");
			System.out.println("---------------------------------------");
			
			((Staff)user).viewEnquiries();
			
        	System.out.print("Pick a Menu : ");
        	choice = scan.nextInt();
        	scan.nextLine();
			
			switch (choice)
			{
			case 1:
			{
				if (((Staff)user).getCreatedCamp().getEnquiries().isEmpty())
				{
					errorChoice();
					System.out.println("Returning to menu..");
					setMenuState(prevMenu);
				}
				else
				{
					((Staff)user).replyEnquiry(scan);
				}
				break;
			}
			case 2:
				setMenuState(prevMenu);
				break;
			}
		}
	}
	
	/**
	 * This method calls the menu to generate report for Participants
	 * @see <code>CommitteeMember</code> for <code>generateParticipantReport()</code> for Committee Member's method implementation
	 * @see <code>Staff</code> for <code>generateParticipantReport()</code> for Staff's method implementation
	 */
	static void generateReportParticipantsMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|          GENERATING REPORT          |");
        System.out.println("---------------------------------------");
		if (user.getRole() == Role.COMMITTEE_MEMBER)
			((CommitteeMember)user).generateParticipantReport(scan);
		else
			((Staff)user).generateParticipantReport(scan);
		
		setMenuState(prevMenu);
	}
	
	// STUDENT METHODS
	
	/**
	 * Method to printing STUDENT main menu and choices
	 */
	static void studentMainMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|        NTU STUDENT MAIN MENU        |");
        System.out.println("---------------------------------------");
        System.out.println("| 1. View Camps                       |");
        System.out.println("| 2. View Enquiries                   |");
        System.out.println("| 3. See Profile                      |");
        System.out.println("| 4. Log Out                          |");
        System.out.println("---------------------------------------");
        System.out.print("Welcome " + user.getRole().toString() + " " + user.getName() + ". Please pick a menu : ");
        
        int choice = scan.nextInt();
        scan.nextLine();
        
        switch(choice)
        {
        case 1:
        {
        	setMenuState(MenuStates.VIEW_CAMPS);
        	break;
        }
        case 2:
        {
        	setMenuState(MenuStates.VIEW_ENQUIRIES);
        	break;
        }
        case 3:
        {
        	setMenuState(MenuStates.PROFILE);
        	break;
        }
        case 4:
        {
        	setMenuState(MenuStates.PRELOG_OUT);
        	break;
        }
        default:
        	errorChoice();
        	break;
        } 
	}
	
	/**
	 * Method to submitting Enquiry as STUDENT / COMMITTEE MEMBER by calling Student's submitEnquiry() method
	 * @see student.Student for <code>submitEnquiry()</code> implementation
	 */
	static void submitEnquiryMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|          CREATING ENQUIRY           |");
        System.out.println("---------------------------------------");
		((Student)user).submitEnquiry(scan, campList);
		setMenuState(prevMenu);
	}

	// COMMITTEE METHODS
	
	/**
	 * Method to printing COMMITTEE MEMBER main menu and choices
	 */
	static void committeeMainMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|       NTU COMMITTEE MAIN MENU       |");
        System.out.println("---------------------------------------");
        System.out.println("| 1. View Available Camps             |");
        System.out.println("| 2. View Enquiries                   |");
        System.out.println("| 3. Submit Suggestion                |");
        System.out.println("| 4. View Suggestions                 |");
        System.out.println("| 5. View Profile                     |");
        System.out.println("| 6. Generate Participant (R)         |");
        System.out.println("| 7. Generate Equiry (R)              |");
        System.out.println("| 8. Log Out                          |");
        System.out.println("---------------------------------------");
        System.out.print("Welcome " + user.getRole().toString() + " " + user.getName() + ". Please pick a menu : ");
        
        int choice = scan.nextInt();
        scan.nextLine();
        
        switch(choice)
        {
        case 1:
        	setMenuState(MenuStates.VIEW_CAMPS);
        	break;
        case 2:
        	setMenuState(MenuStates.VIEW_ENQUIRIES);
        	break;
        case 3:
        	setMenuState(MenuStates.SUBMIT_SUGGESTION);
        	break;
        case 4:
        	setMenuState(MenuStates.VIEW_SUGGESTIONS);
        	break;
        case 5:
        	setMenuState(MenuStates.PROFILE);
        	break;
        case 6:
        	setMenuState(MenuStates.REPORT_PARTICIPANT);
        	break;
        case 7:
        	setMenuState(MenuStates.REPORT_ENQUIRY);
        	break;
        case 8:
        	setMenuState(MenuStates.PRELOG_OUT);
        	break;
        default:
        	errorChoice();
        	break;
        }
	}
	
	/**
	 * Method to print Submit Suggestion menu and call submitSuggestion(Scanner) from CommitteeMember class
	 * @see <code>CommitteeMember</code> for <code>submitSuggestion(Scanner)</code> implementation
	 */
	static void submitSuggestionMenu()
	{
    	System.out.println("---------------------------------------");
        System.out.println("|          SUBMIT SUGGESTION          |");
        System.out.println("---------------------------------------");
        
        ((CommitteeMember)user).submitSuggestion(scan);
        
		setMenuState(MenuStates.CM_MAIN_MENU);
	}
	
	// STAFF METHODS
	/**
	 * Method to printing STAFF main menu and choices
	 */
    static void staffMainMenu()
	{
    	System.out.println("---------------------------------------");
        System.out.println("|         NTU STAFF MAIN MENU         |");
        System.out.println("---------------------------------------");
        if (((Staff)user).hasCreatedCamp() == false)
        System.out.println("| 1. Create Camp                      |");
        else
        System.out.println("| 1. View Camp Details                |");
        System.out.println("| 2. Delete Camp                      |");
        System.out.println("| 3. View Camps (School)              |");
        System.out.println("| 4. View Enquiries                   |");
        System.out.println("| 5. View Suggestions                 |");
        System.out.println("| 6. Generate Participant (R)         |");
        System.out.println("| 7. Generate Performance (R)         |");
        System.out.println("| 8. Generate Equiry (R)              |");
        System.out.println("| 9. View Profile                     |");
        System.out.println("| 10. Log Out                         |");
        System.out.println("---------------------------------------");
        System.out.print("Welcome " + user.getRole().toString() + " " + user.getName() + ". Please pick a menu : ");
		
		int choice = scan.nextInt();
		scan.nextLine();
		switch (choice)
		{
		case 1:
		{
			if (((Staff)user).getCreatedCamp() == null)
				setMenuState(MenuStates.CREATE_CAMP);
			else
				setMenuState(MenuStates.CAMP_DETAILS);
			break;
		}
		case 2:
		{
			setMenuState(MenuStates.DELETE_CAMP);
			break;
		}
		case 3:
		{
			setMenuState(MenuStates.VIEW_CAMPS);
			break;
		}
		case 4:
		{
			setMenuState(MenuStates.VIEW_ENQUIRIES);
			break;
		}
		case 5:
		{
			setMenuState(MenuStates.VIEW_SUGGESTIONS);
			break;
		}
		case 6:
		{
			setMenuState(MenuStates.REPORT_PARTICIPANT);
			break;
		}
		case 7:
		{
			setMenuState(MenuStates.REPORT_PERFORMANCE);
			break;
		}
		case 8:
		{
			setMenuState(MenuStates.REPORT_ENQUIRY);
			break;
		}
		case 9:
		{
			setMenuState(MenuStates.PROFILE);
			break;
		}
		case 10:
		{
			setMenuState(MenuStates.PRELOG_OUT);
			break;
		}
		default:
		{
			errorChoice();
			break;
		}
		}
	}
	
    /**
     * Method to printing Edit Camp menu 
     * 
     * @see <code>Staff</code> for <code>editCamp()</code> method implementation 
     * @see <code>Camp</code> for <code>setVisibleOn/setVisibileOff</code> method implementation
     */
    static void editCampMenu()
    {
    	System.out.println("---------------------------------------");
        System.out.println("|            EDITING CAMP             |");
        System.out.println("---------------------------------------");
        ((Staff)user).editCamp(scan);
        
        System.out.println("Set camp to visible? Enter 1 to set visible, otherwise camp will be invisible.");
        System.out.print("Choice : ");
        int choice = scan.nextInt();
        scan.nextLine();
        
        if (choice == 1)
        	((Staff)user).getCreatedCamp().setVisibleOn();
        else
        	((Staff)user).getCreatedCamp().setVisibleOff();
        
        System.out.println();
    	setMenuState(MenuStates.STAFF_MAIN_MENU);
    }
    
    /**
     * Method to printing create camp menu
     * Also, Staff create camp implementation here
     * 
     * @see <code>Staff</code> for <code>createCamp</code> implementation
     * @see <code>Camp</code> for <code>setVisibility</code> implementation
     */
    static void createCampMenu()
    {
		System.out.println("---------------------------------------");
        System.out.println("|            CREATING CAMP            |");
        System.out.println("---------------------------------------");
        
        ((Staff)user).createCamp(scan);
        campList.add(((Staff)user).getCreatedCamp());
        
        System.out.println("Set Camp to Visible? Press 1 to set visbile, otherwise, camp will be unvisible.");
        System.out.print("Choice : ");
        
        int choice = scan.nextInt();
        scan.nextLine();
        
        if (choice == 1)
        	((Staff)user).getCreatedCamp().setVisibleOn();
        
        System.out.println();
        
    	setMenuState(prevMenu);
    }
    
    /**
     * Method to printing delete camp menu
     * @see <code>Staff</code> for <code>deleteCamp</code> implementation
     */
    static void deleteCampMenu()
    {
		System.out.println("---------------------------------------");
        System.out.println("|            DELETING CAMP            |");
        System.out.println("---------------------------------------");
        System.out.println("| 1. Delete Camp                      |");
        System.out.println("| 2. Back                             |");
        System.out.println("---------------------------------------");
        System.out.print("Deleting Camp. Confirm? Pick a number : ");
        int choice = scan.nextInt();
        scan.nextLine();
        
        if (choice == 1)
        {
        	campList.remove(((Staff)user).getCreatedCamp());
        	((Staff)user).deleteCamp();
        }
        else if (choice < 1 && choice > 2)
        {
        	errorChoice();
        	return;
        }
    	setMenuState(prevMenu);
    }

    /**
     * Method to printing Suggestion Menu for Committee Member and Staff
     * 
     * @see CommitteeMember class for suggestion method implementation
     * @see Staff class for approve suggetion method implementation
     */
    static void viewSuggestionsMenu()
    {
    	System.out.println("---------------------------------------");
        System.out.println("|          SUGGESTION MENU            |");
        System.out.println("---------------------------------------");
        
        int choice;
        
        if (user.getRole() == Role.COMMITTEE_MEMBER)
        {
        	System.out.println("| 1. Create Suggestion                |");
        	System.out.println("| 2. Edit Suggestion                  |");
        	System.out.println("| 3. Delete Suggestion                |");
        	System.out.println("| 4. Back                             |");
        	System.out.println("---------------------------------------");
        	((CommitteeMember)user).viewSuggestions();
        	
        	System.out.print("Pick a menu : ");
        	choice = scan.nextInt();
        	scan.nextLine();
        	
        	switch(choice)
        	{
        	case 1:
        		((CommitteeMember)user).submitSuggestion(scan);
        		break;
        	case 2:
        		((CommitteeMember)user).editSuggestion(scan);
        		break;
        	case 3:
        		((CommitteeMember)user).deleteSuggestion(scan);
        	case 4:
        		break;
        	default:
        		errorChoice();
        		break;
        	}
        	setMenuState(prevMenu);
        }
        else
        {
        	System.out.println("| 1. Approve Suggestion               |");
        	System.out.println("| 2. Back                             |");
        	System.out.println("---------------------------------------");
        	((Staff)user).viewSuggestions();
        	
        	System.out.println("Pick a menu : ");
        	choice = scan.nextInt();
        	scan.nextLine();
        	
        	switch (choice)
        	{
        	case 1:
        		if (((Staff)user).getCreatedCamp().getSuggestions() != null)
        			((Staff)user).approve(scan);
        		else
        			System.out.println("There is no SUGGESTION! Returning to menu.");
        		setMenuState(prevMenu);
        		break;
        	case 2:
        		setMenuState(prevMenu);
        		break;
        	default:
        		errorChoice();
        		break;
        	}
        }
    }

    /**
     * This menu calls the menu to generate report for Performance
     * @see Staff class for <code>generatePerformaceReport()</code> method implementation
     */
    static void generateReportPerformanceMenu()
    {
    	System.out.println("---------------------------------------");
        System.out.println("|          GENERATING REPORT          |");
        System.out.println("---------------------------------------");
    	((Staff)user).generatePerformanceReport(scan);
    	setMenuState(prevMenu);
    }
    
    /**
     * This menu calls the menu to generate report for Enquiries
     * @see <code>CommitteeMember</code> for <code>generateEnquiryReport</code> method implementation
     * @see <code>Staff</code> for <code>generateEnquiryReport</code> method implementation
     */
	static void generateReportEnquiryMenu() {
		System.out.println("---------------------------------------");
        System.out.println("|          GENERATING REPORT          |");
        System.out.println("---------------------------------------");
		
        if (user.getRole() == Role.COMMITTEE_MEMBER)
        	((CommitteeMember)user).generateEnquiryReport(scan);
        else
	        ((Staff)user).generateEnquiryReport(scan);
        setMenuState(prevMenu);
	}

}
