import java.io.*;
import java.util.*;

public class Main {
    static final int CAPACITY = 100; // Using final for constants
    static int studentCount = 0;
    static Student[] students = new Student[CAPACITY];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine()); // Handle non-integer input
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            switch (choice) {
                case 1:
                    checkAvailableSeats();
                    break;
                case 2:
                    registerStudent(scanner);
                    break;
                case 3:
                    deleteStudent(scanner);
                    break;
                case 4:
                    findStudent(scanner);
                    break;
                case 5:
                    storeStudentDetailsToFile();
                    break;
                case 6:
                    loadStudentDetailsFromFile();
                    break;
                case 7:
                    viewListOfStudents();
                    break;
                case 8:
                    extraControls(scanner);
                    break;
                case 0:
                    try {
                        System.out.println("Are you sure you want to exit? (y/n)");
                        String answer = scanner.nextLine();
                        if (answer.equals("y")) {
                            System.out.println("Goodbye!");
                            System.exit(0);
                        }
                    } catch (Exception e) {
                        System.out.println("Enter a valid input.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

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
            String choice = scanner.nextLine().toLowerCase();
            switch (choice) {
                case "a":
                    addStudentName(scanner);
                    break;
                case "b":
                    enterModuleMarks(scanner);
                    break;
                case "c":
                    generateSummary();
                    break;
                case "d":
                    generateCompleteReport();
                    break;
                case "e":
                    return; // Exit the extra controls menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void checkAvailableSeats() {
        System.out.println("Available seats: " + (CAPACITY - studentCount));
    }

    static void registerStudent(Scanner scanner) {
        if (studentCount >= CAPACITY) {
            System.out.println("No available seats.");
            return;
        }
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        students[studentCount++] = new Student(id, name);
        System.out.println("Student registered successfully.");
    }

    static void deleteStudent(Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine();
        boolean found = false;
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                for (int j = i; j < studentCount - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[--studentCount] = null;
                found = true;
                System.out.println("Student deleted successfully.");
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found.");
        }
    }

    static void findStudent(Scanner scanner) {
        System.out.print("Enter student ID to find: ");
        String id = scanner.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                System.out.println("Student ID: " + students[i].getId() + ", Name: " + students[i].getName());
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void storeStudentDetailsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"))) {
            // Write header
            writer.write(String.format("%-5s %-10s %-20s %-10s %-10s %-10s %-10s %-10s", "No", "ID", "Name", "Average", "Grade", "Module1", "Module2", "Module3"));
            writer.newLine();
            writer.write("------------------------------------------------------------------------------------------");
            writer.newLine();

            // Write student details
            for (int i = 0; i < studentCount; i++) {
                Student s = students[i];
                writer.write(String.format("%-5d %-10s %-20s %-10.3f %-10s %-10d %-10d %-10d",
                        (i + 1), s.getId(), s.getName(), s.getAverage(), s.getGrade(), s.getModules()[0].getMark(), s.getModules()[1].getMark(), s.getModules()[2].getMark()));
                writer.newLine();
            }
            System.out.println("Student details stored to file.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }




    static void loadStudentDetailsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            studentCount = 0;
            boolean skipHeader = true;
            while ((line = reader.readLine()) != null) {
                // Skip the header line
                if (skipHeader || line.startsWith("-")) {
                    skipHeader = false;
                    continue;
                }
                String[] parts = line.trim().split("\\s+");
                if (parts.length < 8) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                String id = parts[1];
                StringBuilder nameBuilder = new StringBuilder(parts[2]);
                for (int i = 3; i < parts.length - 5; i++) {
                    nameBuilder.append(" ").append(parts[i]);
                }
                String name = nameBuilder.toString();
                double average = Double.parseDouble(parts[parts.length - 5]);
                String grade = parts[parts.length - 4];
                int mark1 = Integer.parseInt(parts[parts.length - 3]);
                int mark2 = Integer.parseInt(parts[parts.length - 2]);
                int mark3 = Integer.parseInt(parts[parts.length - 1]);

                int[] marks = {mark1, mark2, mark3};
                students[studentCount] = new Student(id, name);
                students[studentCount++].setMarks(marks);
            }
            System.out.println("Student details loaded from file.");
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing marks: " + e.getMessage());
        }
    }



    static void viewListOfStudents() {
        // sort students by id
        Arrays.sort(students, 0, studentCount, Comparator.comparing(Student::getId));
        for (int i = 0; i < studentCount; i++) {
            System.out.println("Student ID: " + students[i].getId() + ", Name: " + students[i].getName());
        }
    }

    static void addStudentName(Scanner scanner) {
        System.out.print("Enter student ID to add name: ");
        String id = scanner.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                System.out.print("Enter new student name: ");
                String name = scanner.nextLine();
                students[i].setName(name);
                System.out.println("Student name updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void enterModuleMarks(Scanner scanner) {
        System.out.print("Enter student ID to enter marks: ");
        String id = scanner.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                int[] marks = new int[3];
                for (int j = 0; j < 3; j++) {
                    System.out.print("Enter marks for module " + (j + 1) + ": ");
                    while (true) {
                        try {
                            marks[j] = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.print("Invalid input. Enter marks for module " + (j + 1) + " again: ");
                        }
                    }
                }
                students[i].setMarks(marks);
                System.out.println("Marks updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void generateSummary() {
        System.out.println("Total student registrations: " + studentCount);
        int module1Pass = 0, module2Pass = 0, module3Pass = 0;
        for (Student student : students) {
            if (student == null) break;
            Module[] modules = student.getModules();
            if (modules[0].getMark() > 40) module1Pass++;
            if (modules[1].getMark() > 40) module2Pass++;
            if (modules[2].getMark() > 40) module3Pass++;
        }
        System.out.println("Students scoring above 40 in Module 1: " + module1Pass);
        System.out.println("Students scoring above 40 in Module 2: " + module2Pass);
        System.out.println("Students scoring above 40 in Module 3: " + module3Pass);
    }

    static void generateCompleteReport() {
        if (studentCount == 0) {
            System.out.println("No student found to generate complete report.");
        } else {
            bubbleSortByAverage();
            for (int i = 0; i < studentCount; i++) {
                Student s = students[i];
                System.out.println("Student ID: " + s.getId() + ", Name: " + s.getName() + ", Average Marks: " + String.format("%.3f", s.getAverage()) + ", Grade: " + s.getGrade());
            }
        }
    }

    static void bubbleSortByAverage() {
        for (int i = 0; i < studentCount - 1; i++) {
            for (int j = 0; j < studentCount - i - 1; j++) {
                if (students[j].getAverage() < students[j + 1].getAverage()) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }
}
