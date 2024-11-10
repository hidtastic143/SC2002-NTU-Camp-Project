package student;

import java.util.ArrayList;
import java.util.Scanner;

import camp.Camp;
import enquiry.BaseEnquiry;
/**
 * The interface for student specific enquiry methods.
 * Submit, edit and delete enquiries.
 * @author Wang Jing
 * @version 1.4
 * @since 2023-11-13
 */
public interface StudentEnquiry extends BaseEnquiry {
    void submitEnquiry(Scanner scanner, ArrayList<Camp> camps);
    void editEnquiry(Scanner scanner);
    void deleteEnquiry(Scanner scanner, ArrayList<Camp> camps);
}