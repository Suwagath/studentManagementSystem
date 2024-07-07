public class Student {
    private String id;
    private String name;
    private Module[] modules;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.modules = new Module[3];
        for (int i = 0; i < 3; i++) {
            this.modules[i] = new Module();
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Module[] getModules() {
        return modules;
    }

    public void setMarks(int[] marks) {
        for (int i = 0; i < 3; i++) {
            this.modules[i].setMark(marks[i]);
        }
    }

    public double getAverage() {
        int sum = 0;
        for (Module module : modules) {
            sum += module.getMark();
        }
        return sum / 3.0;
    }

    public String getGrade() {
        double average = getAverage();
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
