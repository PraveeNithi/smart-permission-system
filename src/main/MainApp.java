package main;

import model.PermissionRequest;
import service.PermissionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PermissionService service = new PermissionService();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        boolean exit = false;
        System.out.println("=== Smart Permission Request System ===");
        
        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Submit Permission Request");
            System.out.println("2. View All Request History");
            System.out.println("3. View History by Employee");
            System.out.println("4. View History by Status");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    submitRequest();
                    break;
                case "2":
                    viewAll();
                    break;
                case "3":
                    viewByEmployee();
                    break;
                case "4":
                    viewByStatus();
                    break;
                case "5":
                    exit = true;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void submitRequest() {
        try {
            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Employee Name: ");
            String employeeName = scanner.nextLine();
            
            System.out.print("Enter Reason: ");
            String reason = scanner.nextLine();
            
            System.out.print("Enter Request Date (yyyy-MM-dd): ");
            Date requestDate = sdf.parse(scanner.nextLine());
            
            System.out.print("Enter Number Of Days: ");
            int numberOfDays = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Leave Type (Medical / Casual / Emergency): ");
            String leaveType = scanner.nextLine();
            
            System.out.print("Enter Past Leave Count: ");
            int pastLeaveCount = Integer.parseInt(scanner.nextLine());
            
            PermissionRequest request = new PermissionRequest(employeeId, employeeName, reason, requestDate, 
                                                              numberOfDays, leaveType, pastLeaveCount);
                                                              
            PermissionRequest processed = service.processRequest(request);
            System.out.println("\n>>> Request Processed Successfully!");
            System.out.println("Assigned Status: " + processed.getStatus());
            System.out.println("Decision Reason: " + processed.getDecisionReason());
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter numerical values where appropriate.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void viewAll() {
        List<PermissionRequest> list = service.getFullHistory();
        printList(list);
    }

    private static void viewByEmployee() {
        System.out.print("Enter Employee ID: ");
        try {
            int empId = Integer.parseInt(scanner.nextLine());
            List<PermissionRequest> list = service.getHistoryByEmployee(empId);
            printList(list);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Employee ID.");
        }
    }

    private static void viewByStatus() {
        System.out.print("Enter Status (APPROVED / REJECTED / REVIEW): ");
        String status = scanner.nextLine().toUpperCase();
        List<PermissionRequest> list = service.getHistoryByStatus(status);
        printList(list);
    }

    private static void printList(List<PermissionRequest> list) {
        if (list.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-10s | %-15s | %-15s | %-10s | %-10s | %-10s | %-15s%n", 
                          "ID", "Emp ID", "Name", "Leave Type", "Days", "Status", "Past Lvs", "Reason (Preview)");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        for (PermissionRequest req : list) {
            String shortReason = req.getReason();
            if (shortReason != null && shortReason.length() > 15) {
                shortReason = shortReason.substring(0, 12) + "...";
            }
            System.out.printf("%-5d | %-10d | %-15s | %-15s | %-10d | %-10s | %-10d | %-15s%n",
                              req.getId(), req.getEmployeeId(), req.getEmployeeName(),
                              req.getLeaveType(), req.getNumberOfDays(), req.getStatus(),
                              req.getPastLeaveCount(), shortReason);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }
}
