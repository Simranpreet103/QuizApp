package quizapp;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class question {

    int questionid;
    String questiontext;
    
    ArrayList<option> options=new ArrayList<option>();
     
    public static ArrayList<question> getquestions(int catid) 
    {
        ArrayList<question> AQ =new ArrayList<question>();
        try
        {
        PreparedStatement ps= Quizapp.con.prepareStatement("select top 10 *from questions where CATEGORYID=? order by newID()");
        ps.setInt(1,catid);
        
        ResultSet rs=ps.executeQuery();
        
        
        
        String ids="";
        
        while(rs.next())
        {
            question q=new question();
            q.questionid=rs.getInt("QUESTIONID");
            q.questiontext=rs.getString("QUESTION");
            
            AQ.add(q);
            ids=ids+q.questionid+",";
            
            
        }//while
        
        rs.close();
        ps.close();
        
        ids=ids.substring(0,ids.length()-1);
        
        ps=Quizapp.con.prepareStatement("select *from options where QUESTIONID In("+ids+") order by QUESTIONID");
        rs=ps.executeQuery();
        
        int qid;
        while(rs.next())
        {
            
            option opt=new option();
            opt.optionid=rs.getInt("OPTIONID");
            opt.optiontext=rs.getString("OPTIONTEXT");
            
            qid=rs.getInt("QUESTIONID");
            
            for(question q:AQ)
            {
                if(q.questionid==qid)
                {
                    q.options.add(opt);
                    break;
                }//if
            }//for
            
        }//while
           rs.close();
           ps.close();
           
           
           
        }//try
        catch(Exception ex)
        {
           JOptionPane.showMessageDialog(null, ex);
        }//catch
        return AQ;
    } 
    
    public static int submittest(int categoryid,ArrayList<attemptedquestion> AQ)
    {
        int CTID=0;
        try
        {
        CallableStatement cs=Quizapp.con.prepareCall("{call submittest(?,?,?)}");
  
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        
        cs.setInt(2,Quizapp.RegId);
        cs.setInt(3, categoryid);
           
        cs.execute();
        
        CTID=cs.getInt(1);
       
        for(attemptedquestion a:AQ)
        {
            PreparedStatement ps=Quizapp.con.prepareStatement("insert into attemptedquestions values(?,?,?)");
            
            ps.setInt(1,a.questionid);
            ps.setInt(2,a.optionid);
            ps.setInt(3, CTID);
            
            ps.execute();
            
        }
        
        
        
        
        }//try
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }//catch
        
        return CTID;
    }//submittest
    
    
    
    
}
