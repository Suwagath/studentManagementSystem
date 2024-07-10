import java.io.BufferedReader; // Importing classes for reading from a file
import java.io.BufferedWriter; // Importing classes for writing to a file
import java.io.FileReader; // Importing classes for file reading
import java.io.FileWriter; // Importing classes for file writing
import java.io.IOException; // Importing class for handling IO exceptions
import java.util.Scanner; // Importing class for taking user input
import java.util.Arrays; // Importing class for sorting arrays
import java.util.Comparator; // Importing class for comparing objects


public class Main {
    // Constant for maximum student capacity
    static final int CAPACITY = 100;
    // Variable to keep track of the number of registered students
    static int studentCount = 0;
    // Array to store student objects
    static Student[] students = new Student[CAPACITY];

    public static void main(String[] args) {
        // Create a Scanner object for reading user input
        Scanner scanner = new Scanner(System.in);
        // Infinite loop to keep showing the menu until user chooses to exit
        while (true) {
            // Show the main menu to the user
            printMenu();
            int choice;
            try {
                // Read the user's menu choice
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Handle non-integer input
                System.out.println("Invalid input. Please enter a number.");
                continue; // Go back to showing the menu
            }
            // Switch statement to handle different menu choices
            switch (choice) {
                case 1:
                    checkAvailableSeats(); // Show available seats
                    break;
                case 2:
                    registerStudent(scanner); // Register a new student
                    break;
                case 3:
                    deleteStudent(scanner); // Delete a student
                    break;
                case 4:
                    findStudent(scanner); // Find a student by ID
                    break;
                case 5:
                    storeStudentDetails(); // Save student details to file
                    break;
                case 6:
                    loadStudentDetails(); // Load student details from file
                    break;
                case 7:
                    viewListOfStudents(); // Show list of students
                    break;
                case 8:
                    extraControls(scanner); // Extra controls for more options
                    break;
                case 0:
                    // Ask the user if they really want to exit
                    try {
                        System.out.println("Are you sure you want to exit? (y/n)");
                        String answer = scanner.nextLine();
                        if (answer.equals("y")) {
                            System.out.println("Goodbye!");
                            System.exit(0); // Exit the program
                        }
                    } catch (Exception e) {
                        System.out.println("Enter a valid input.");
                    }
                    break;
                default:
                    // Handle invalid menu choices
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to print the main menu
    static void printMenu() {
        System.out.println("=====================================");
        System.out.println("|       Student Management System   |");
        System.out.println("=====================================");
        System.out.println("| 1. Check available seats          |");
        System.out.println("| 2. Register student               |");
        System.out.println("| 3. Delete student                 |");
        System.out.println("| 4. Find student                   |");
        System.out.println("| 5. Store student details to file  |");
        System.out.println("| 6. Load student details from file |");
        System.out.println("| 7. View list of students by name  |");
        System.out.println("| 8. Extra controls                 |");
        System.out.println("| 0. Exit                           |");
        System.out.println("=====================================");
        System.out.print("Enter your choice: ");
    }

    // Method to show the extra controls menu and handle user choices
    static void extraControls(Scanner scanner) {
        while (true) {
            System.out.println("=====================================");
            System.out.println("|           Extra Controls          |");
            System.out.println("=====================================");
            System.out.println("| a. Add student name               |");
            System.out.println("| b. Enter module marks             |");
            System.out.println("| c. Generate summary report        |");
            System.out.println("| d. Generate complete report       |");
            System.out.println("| e. Return to main menu            |");
            System.out.println("=====================================");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().toLowerCase(); // Convert choice to lowercase for consistency
            switch (choice) {
                case "a":
                    addStudentName(scanner); // Add or update student name
                    break;
                case "b":
                    enterModuleMarks(scanner); // Enter module marks for a student
                    break;
                case "c":
                    generateSummary(); // Generate a summary report
                    break;
                case "d":
                    generateCompleteReport(); // Generate a complete report
                    break;
                case "e":
                    return; // Exit the extra controls menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to check and display the number of available seats
    static void checkAvailableSeats() {
        System.out.println("Available seats: " + (CAPACITY - studentCount));
    }

    // Method to register a new student
    static void registerStudent(Scanner scanner) {
        // Check if there are available seats
        if (studentCount >= CAPACITY) {
            System.out.println("No available seats.");
            return; // Exit the method if no seats are available
        }


        // Prompt the user to enter student ID and name
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        if (id == null || id.isEmpty()) {
            System.out.println("Invalid ID. Please try again.");
            return; // Exit the method if ID is invalid
        }
        // Check if the student ID already exists
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                System.out.println("Student ID already exists. Please try again.");
                return; // Exit the method if ID already exists
            }
        }
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        // Add the new student to the array
        students[studentCount++] = new Student(id, name);
        System.out.println("Student registered successfully.");
    }

    // Method to delete a student by ID
    static void deleteStudent(Scanner scanner) {
        if (studentCount == 0) {
            System.out.println("No student found to delete.");
            return; // Exit the method if no students are registered
        }
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine();
        boolean found = false; // Flag to check if student was found
        // Loop through the students array to find the student by ID
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                // Shift remaining students to fill the gap
                for (int j = i; j < studentCount - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[--studentCount] = null; // Decrease count and set last element to null
                found = true;
                System.out.println("Student deleted successfully.");
                break; // Exit the loop once the student is deleted
            }
        }
        if (!found) {
            System.out.println("Student not found.");
        }
    }

    // Method to find and display a student by ID
    static void findStudent(Scanner scanner) {
        if (studentCount == 0) {
            System.out.println("No student found to search.");
            return; // Exit the method if no students are registered
        }
        System.out.print("Enter student ID to find: ");
        String id = scanner.nextLine();
        // Loop through the students array to find the student by ID
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                System.out.println("Student ID: " + students[i].getId() + ", Name: " + students[i].getName());
                return; // Exit the method once the student is found
            }
        }
        System.out.println("Student not found.");
    }

    // Method to store student details to a file
    static void storeStudentDetails() {
        if (studentCount == 0) {
            System.out.println("No student details to store.");
            return; // Exit the method if no students are registered
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"))) {
            // Write the header for the file
            writer.write(String.format("%-5s %-10s %-20s %-10s %-15s %-10s %-10s %-10s", "No", "ID", "Name", "Average", "Grade", "Module1", "Module2", "Module3"));
            writer.newLine(); // Move to the next line
            writer.write("-------------------------------------------------------------------------------------------------");
            writer.newLine();
            // Write the details of each student
            for (int i = 0; i < studentCount; i++) {
                Student s = students[i];
                writer.write(String.format("%-5d %-10s %-20s %-10.3f %-15s %-10d %-10d %-10d",
                        (i + 1), s.getId(), s.getName(), s.getAverage(), s.getGrade(), s.getModules()[0].getMark(), s.getModules()[1].getMark(), s.getModules()[2].getMark()));
                writer.newLine();
            }
            System.out.println("Student details stored to file.");
        } catch (IOException err) {
            System.out.println("Error writing to file: " + err.getMessage());
        }
    }

    // Method to load student details from a file
    static void loadStudentDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            studentCount = 0; // Reset student count before loading
            boolean skipHeader = true; // Flag to skip the header line
            while ((line = reader.readLine()) != null) {
                if (skipHeader || line.startsWith("-")) {
                    skipHeader = false; // Skip the first header line
                    continue;
                }
                // Split the line into parts based on whitespace
                String[] parts = line.trim().split("\\s+");
                if (parts.length < 8) {
                    System.out.println("Skipping invalid line: " + line);
                    continue; // Skip lines that don't have enough data
                }
                // Extract student details from the parts
                String id = parts[1];
                String name = parts[2];
                for (int i = 3; i < parts.length - 5; i++) {
                    name += " " + parts[i]; // Reconstruct the name if it contains spaces
                }
                int module1 = Integer.parseInt(parts[parts.length - 3]);
                int module2 = Integer.parseInt(parts[parts.length - 2]);
                int module3 = Integer.parseInt(parts[parts.length - 1]);
                // Create a new student object and set its marks
                students[studentCount] = new Student(id, name);
                students[studentCount].setMarks(new int[]{module1, module2, module3});
                studentCount++;
            }
            System.out.println("Student details loaded from file.");
        } catch (IOException err) {
            System.out.println("Error reading from file: " + err.getMessage());
        }
    }

    // Method to display a list of students sorted by name
    static void viewListOfStudents() {
        if (studentCount == 0) {
            System.out.println("No students registered.");
            return; // Exit the method if no students are registered
        }
            // Sort the students array by name
            Arrays.sort(students, 0, studentCount, Comparator.comparing(Student::getName));
            // Display each student's ID and name
            for (int i = 0; i < studentCount; i++) {
                System.out.println("Student ID: " + students[i].getId() + ", Name: " + students[i].getName());
            }

    }

    // Method to add or update a student's name by ID
    static void addStudentName(Scanner scanner) {
        if (studentCount == 0) {
            System.out.println("No student found to update name.");
            return; // Exit the method if no students are registered
        }
        System.out.print("Enter student ID to update name: ");
        String id = scanner.nextLine();
        // Loop through the students array to find the student by ID
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                System.out.print("Enter new student name: ");
                String name = scanner.nextLine();
                students[i].setName(name); // Update the student's name
                System.out.println("Student name updated successfully.");
                return; // Exit the method once the name is updated
            }
        }
        System.out.println("Student not found.");
    }

    // Method to enter marks for a student by ID
    static void enterModuleMarks(Scanner scanner) {
        if (studentCount == 0) {
            System.out.println("No student found to enter marks.");
            return; // Exit the method if no students are registered
        }
            System.out.print("Enter student ID to enter marks: ");
            String id = scanner.nextLine();
            // Loop through the students array to find the student by ID
            for (int i = 0; i < studentCount; i++) {
                if (students[i].getId().equals(id)) {
                    int[] marks = new int[3];
                    // Prompt the user to enter marks for each module
                    for (int j = 0; j < 3; j++) {
                        System.out.print("Enter marks for module " + (j + 1) + ": ");
                        while (true) {
                            try {
                                marks[j] = Integer.parseInt(scanner.nextLine());
                                break; // Exit the loop once valid marks are entered
                            } catch (NumberFormatException e) {
                                System.out.print("Invalid input. Enter marks for module " + (j + 1) + " again: ");
                            }
                        }
                    }
                    students[i].setMarks(marks); // Update the student's marks
                    System.out.println("Marks updated successfully.");
                    return; // Exit the method once the marks are updated
                }
            }
            System.out.println("Student not found.");
        }


    // Method to generate a summary report of student performance
    static void generateSummary() {
        if (studentCount == 0) {
            System.out.println("No student found to generate summary report.");
            return;
        }
        System.out.println("Total student registrations: " + studentCount);
        int module1Pass = 0, module2Pass = 0, module3Pass = 0;
        // Count the number of students passing each module
        for (Student student : students) {
            if (student == null) break; // Stop if null (end of array)
            Module[] modules = student.getModules();
            if (modules[0].getMark() > 40) module1Pass++;
            if (modules[1].getMark() > 40) module2Pass++;
            if (modules[2].getMark() > 40) module3Pass++;
        }
        System.out.println("Students scoring above 40 in Module 1: " + module1Pass);
        System.out.println("Students scoring above 40 in Module 2: " + module2Pass);
        System.out.println("Students scoring above 40 in Module 3: " + module3Pass);
    }

    // Method to generate a complete report of all students sorted by average marks
    static void generateCompleteReport() {
        if (studentCount == 0) {
            System.out.println("No student found to generate complete report.");
        } else {
            // Sort students by average marks
            bubbleSortByAverage();
            // Display header
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
            System.out.println(String.format("%-5s %-20s %-23s %-10s %-10s %-10s %-10s %-10s %-10s",
                    "No", "Student ID", "Name", "Module 1", "Module 2", "Module 3", "Total", "Average", "Grade"));
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
            // Display each student's details with numbering
            for (int i = 0; i < studentCount; i++) {
                Student s = students[i];
                int total = s.getModules()[0].getMark() + s.getModules()[1].getMark() + s.getModules()[2].getMark();
                System.out.println(String.format("%-5d %-20s %-23s %-10d %-10d %-10d %-10d %-10.3f %-10s",
                        (i + 1), s.getId(), s.getName(),
                        s.getModules()[0].getMark(), s.getModules()[1].getMark(), s.getModules()[2].getMark(),
                        total, s.getAverage(), s.getGrade()));
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        }
    }


    // Method to sort students by average marks in descending order using bubble sort
    static void bubbleSortByAverage() {
        for (int i = 0; i < studentCount - 1; i++) {
            for (int j = 0; j < studentCount - i - 1; j++) {
                if (students[j].getAverage() < students[j + 1].getAverage()) {
                    // Swap students if they are in the wrong order
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }


}