package studentClient.simpledb;
import java.sql.*;
import simpledb.remote.SimpleDriver;

public class CreateShipsTables {
    public static void main(String[] args) {
		Connection conn = null;
		try {
			Driver d = new SimpleDriver();
			conn = d.connect("jdbc:simpledb://localhost", null);
			Statement stmt = conn.createStatement();

			String s = "create table classes(classId varchar(20), type varchar(2), country varchar(20), numGuns int, bore int, disp int)";
			stmt.executeUpdate(s);
			System.out.println("Table Classes created.");
			
			s = "create table battles(name varchar(20), date varchar(20))";
			stmt.executeUpdate(s);
			System.out.println("Table Battles created.");

			s = "create table outcomes(ship varchar(20),battle varchar(20),result varchar(10))";
			stmt.executeUpdate(s);
			System.out.println("Table Outcomes created.");
			
			s = "create table ships(name varchar(20), class varchar(20), launched int)";
			stmt.executeUpdate(s);
			System.out.println("Table Ships created.");		
			}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("done.");
			try {
				if (conn != null)
					conn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
