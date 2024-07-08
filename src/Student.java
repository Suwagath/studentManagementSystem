public class Student {
    // Instance variables for student ID, name, and modules
    private String id;
    private String name;
    private Module[] modules;

    // Constructor to initialize the student object with ID and name
    public Student(String id, String name) {
        this.id = id; // Set the student's ID
        this.name = name; // Set the student's name
        this.modules = new Module[3]; // Initialize the modules array to hold only 3 Module objects
        // Create a new Module object for the 3 module slots
        for (int i = 0; i < 3; i++) {
            this.modules[i] = new Module();
        }
    }

    // Method to get student ID
    public String getId() {
        return id;
    }

    // Method to get student name
    public String getName() {
        return name;
    }

    // Method to set student name
    public void setName(String name) {
        this.name = name;
    }

    // Method to get array of modules
    public Module[] getModules() {
        return modules;
    }

    // Method to set the marks for each module
    public void setMarks(int[] marks) {
        // Loop through each module and set its mark
        for (int i = 0; i < 3; i++) {
            this.modules[i].setMark(marks[i]);
        }
    }

    // Method to calculate and return the average mark for all modules
    public double getAverage() {
        int sum = 0; // Variable to hold the sum of all module marks
        // Loop through each module to sum up the marks
        for (Module module : modules) {
            sum += module.getMark();
        }
        // Calculate and return the average mark
        return sum / 3.0;
    }

    // Method to assign and return the student's grade
    public String getGrade() {
        double average = getAverage(); // Get the average mark
        // Determine the grade based on the average mark
        if (average >= 80) {
            return "Distinction";
        } else if (average >= 70) {
            return "Merit";
        } else if (average >= 40) {
            return "Pass";
        } else {
            return "Fail";
        }
    }
}
