package pkt1;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class AttendanceDao{
	
	
	
	public static Connection getConnection(){  
        Connection con=null;  
        try{  
            Class.forName("org.hsqldb.jdbcDriver");  
            con=DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/","sa","");  
        }catch(Exception e){System.out.println(e);}  
        return con;  
	}

	
	
public static String DateConverter(String oldDateString){
	
	final String OLD_FORMAT = "dd-MM-yyyy";
	final String NEW_FORMAT = "yyyy-MM-dd";

	// August 12, 2010
	
	String newDateString;

	SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
	Date d = null;
	try {
		d = (Date) sdf.parse(oldDateString);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	sdf.applyPattern(NEW_FORMAT);
	newDateString = sdf.format(d);
	
	return newDateString;
	
}
	
	
	public static List<Attendance> getAllAttendanceDetail(String name, String password ,String Cname,String dFrom ,String dTo) {
		
		dFrom=DateConverter(dFrom);
		dTo=DateConverter(dTo);
		Date dSqlFrom=Date.valueOf(dFrom);
		Date dSqlTo=Date.valueOf(dTo);
		
		
		List<Attendance> list = new ArrayList<Attendance>();
		
		try{
			Connection con = AttendanceDao.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from Attendance where Cname=? and date >= ? and date <= ? and uid=(select uid from student where name=? and password=?);");
			  ps.setString(1, Cname);
			  ps.setDate(2, (java.sql.Date)dSqlFrom);
			  ps.setDate(3, (java.sql.Date)dSqlTo);
			  ps.setString(4, name);
			  ps.setString(5, password);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Attendance a = new Attendance();
				a.setUid(rs.getInt(1));
		//change required
				a.setCname(rs.getString(2));
				a.setDate(rs.getDate(3));
				a.setStatus(rs.getString(4));
				list.add(a);
				
			
				
			}
			con.close();	
		}catch(Exception ex){ex.printStackTrace();}
		
		return list;
	}

	
}
