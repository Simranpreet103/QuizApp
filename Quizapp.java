package quizapp;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Quizapp {
    
    static int RegId;
    static int CatID;
    public static Connection con=null;
    
    public static void main(String[] args) {

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con=DriverManager.getConnection("jdbc:sqlserver://misha-PC\\KRITIKA:49168;databaseName=registration;username=sa;password=kritika");
        
            form1 obj=new form1();
            obj.setVisible(true);
        }
        
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
}
