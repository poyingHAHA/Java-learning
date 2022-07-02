
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    private static  Connection c;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        initailizeD();

        String name = JOptionPane.showInputDialog("Find a video by name.");
        String sql_statement = "select * from video where videoName = ?;"; // ? is for parameterize which can prevent sql injection
        PreparedStatement pps = c.prepareStatement(sql_statement);
        pps.setString(1, name); // start from 1
        ResultSet rs = pps.executeQuery();
        if(rs.next()){
            int title = Integer.parseInt(rs.getString("videoId"));
            String vname = rs.getString("videoName");
            int price = Integer.parseInt(rs.getString("price"));
            JOptionPane.showMessageDialog(null, new Video(title, vname, price));
        }else{
            JOptionPane.showMessageDialog(null, "Video not found");
        }

        closeDB();
    }

    private static void initailizeD() throws SQLException {
        c = DriverManager.getConnection("jdbc:mysql://localhost:3306/edwindb", "edwin", "edwin0516");

        if(c != null){
            System.out.println("Connecting to the database");
        }else{
            System.out.println("Cannot connect to database");
        }
    }

    private static void closeDB() throws SQLException {
        c.close();
    }
}