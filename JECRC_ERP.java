import java.sql.*;
import java.util.Scanner;

public class JECRC_ERP {
    static Connection con = DBConnection.getConnection();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Add Student\n2. Add Class Schedule\n3. Mark Attendance\n4. Add Exam Results\n5. View Student Details\n6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: addStudent(); break;
                case 2: addClassSchedule(); break;
                case 3: markAttendance(); break;
                case 4: addResults(); break;
                case 5: viewStudentDetails(); break;
                case 6: System.exit(0);
                default: System.out.println("Invalid Choice! Try again.");
            }
        }
    }

    // 1️⃣ Add Student
    public static void addStudent() {
        try {
            System.out.print("Enter Roll No: ");
            int roll = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Course: ");
            String course = sc.nextLine();
            System.out.print("Enter Batch: ");
            String batch = sc.nextLine();
            System.out.print("Enter Contact: ");
            String contact = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("INSERT INTO students VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, roll);
            ps.setString(2, name);
            ps.setString(3, course);
            ps.setString(4, batch);
            ps.setString(5, contact);
            ps.executeUpdate();
            System.out.println("Student Added Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2️⃣ Add Class Schedule
    public static void addClassSchedule() {
        try {
            sc.nextLine();
            System.out.print("Enter Day (Mon-Fri): ");
            String day = sc.nextLine();
            System.out.print("Enter Time Slot (e.g., 10:00-11:00): ");
            String time = sc.nextLine();
            System.out.print("Enter Subject: ");
            String subject = sc.nextLine();
            System.out.print("Enter Faculty Name: ");
            String faculty = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("INSERT INTO class_schedule (day, time_slot, subject, faculty) VALUES (?, ?, ?, ?)");
            ps.setString(1, day);
            ps.setString(2, time);
            ps.setString(3, subject);
            ps.setString(4, faculty);
            ps.executeUpdate();
            System.out.println("Class Schedule Added Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3️⃣ Mark Attendance
    public static void markAttendance() {
        try {
            System.out.print("Enter Roll No: ");
            int roll = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Date (YYYY-MM-DD): ");
            String date = sc.nextLine();
            System.out.print("Enter Status (Present/Absent): ");
            String status = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("INSERT INTO attendance VALUES (?, ?, ?)");
            ps.setInt(1, roll);
            ps.setString(2, date);
            ps.setString(3, status);
            ps.executeUpdate();
            System.out.println("Attendance Marked Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4️⃣ Add Exam Results
    public static void addResults() {
        try {
            System.out.print("Enter Roll No: ");
            int roll = sc.nextInt();
            System.out.print("Enter In-Sem Marks: ");
            int inSem = sc.nextInt();
            System.out.print("Enter End-Sem Marks: ");
            int endSem = sc.nextInt();
            System.out.print("Enter Practical Marks: ");
            int practical = sc.nextInt();
            int total = inSem + endSem + practical;

            PreparedStatement ps = con.prepareStatement("INSERT INTO results VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, roll);
            ps.setInt(2, inSem);
            ps.setInt(3, endSem);
            ps.setInt(4, practical);
            ps.setInt(5, total);
            ps.executeUpdate();
            System.out.println("Results Added Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5️⃣ View Student Details
    public static void viewStudentDetails() {
        try {
            System.out.print("Enter Roll No: ");
            int roll = sc.nextInt();

            // Fetch student details
            PreparedStatement ps = con.prepareStatement("SELECT * FROM students WHERE roll_no = ?");
            ps.setInt(1, roll);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("\nRoll No: " + rs.getInt(1));
                System.out.println("Name: " + rs.getString(2));
                System.out.println("Course: " + rs.getString(3));
                System.out.println("Batch: " + rs.getString(4));
                System.out.println("Contact: " + rs.getString(5));
            } else {
                System.out.println("Student Not Found!");
                return;
            }

            // Fetch results
            ps = con.prepareStatement("SELECT * FROM results WHERE roll_no = ?");
            ps.setInt(1, roll);
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Marks: In-Sem(" + rs.getInt(2) + "), End-Sem(" + rs.getInt(3) + "), Practical(" + rs.getInt(4) + "), Total(" + rs.getInt(5) + ")");
            } else {
                System.out.println("No Exam Results Found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
