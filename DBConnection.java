import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    static Connection con;

    public static Connection getConnection() {
        try {
            if (con == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JECRC_ERP", "root", "1234");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
