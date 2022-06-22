
import java.sql.*; 

public class SqlCon {
	public static void main(String[] args) {
		System.out.println("hello");
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/finalproject","root","password");  
			
			Statement stmt=con.createStatement();  
			String iQ = "insert into course values('5','java', 'java')";
			String dQ = "delete from course where courseid =5";
			String uQ= "update course set coursedescription = 'updated description' where courseid =5";
	//	stmt.executeUpdate(dQ); // insert query
			ResultSet rs=stmt.executeQuery("select ISBN, Title, Retail from BOOKS");  
			System.out.println("ISBN\t Title\tRetail");
			while(rs.next())  
			System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3));  
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
			}  
	}


