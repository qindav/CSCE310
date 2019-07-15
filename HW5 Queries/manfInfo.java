import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class manfInfo {
	public static void main(String[] args) {
		//Parameters
		String DBLocation = "database-new.cse.tamu.edu";// The host 
		String DBname = "dqin-manfDB"; //Generally your CS username or username-text like explained above
		String DBUser = "dqin"; //CS username
		String DBPass = "Cheese5378"; //password setup via CSNet for the MySQL database

		Connection conn = null;
		try {
			//Connection
			String connectionString = "jdbc:mysql://"+DBLocation+"/"+DBname;
			Class.forName ("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(connectionString, DBUser, DBPass);
			System.out.println ("Database connection established");
			
			//Read manf from user
			InputStreamReader in = new InputStreamReader(System.in);
		    BufferedReader keyboard = new BufferedReader(in);
		    System.out.print("Enter the manufacturer: ");
		    String manf = keyboard.readLine();
		    
			//The three different reads
			String a = "" 
					+ "SELECT product.maker, product.type,pcvals.* "
					+ "FROM pc as pcvals,product "
					+ "WHERE pcvals.model = product.model "
					+ "AND product.maker = '" + manf + "';";
			
			String b = "" 
					+ "SELECT product.maker, product.type,printervals.* "
					+ "FROM printer as printervals,product "
					+ "WHERE printervals.model = product.model "
					+ "AND product.maker = '" + manf + "';";
			
			String c = "" 
					+ "SELECT product.maker, product.type,laptopvals.* "
					+ "FROM laptop as laptopvals,product "
					+ "WHERE laptopvals.model = product.model "
					+ "AND product.maker = '" + manf + "';";
			doQuery(conn,a, "PC TABLE");
			doQuery(conn,b, "PRINTER TABLE");
			doQuery(conn,c, "LAPTOP TABLE");
			
		}
		catch (Exception e) {
			System.out.println("Connection Issue: " + e.getMessage());
		}
	}
	
	public static void doQuery(Connection conn,String query, String tableName) {
		try {
			//Create statement to execute
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
		    ResultSetMetaData rsmd = rs.getMetaData();
		    
		    System.out.println(tableName);
		    if(!rs.next()) {
		    	System.out.println("No records in this table");
		    	System.out.println();
		    	return;
		    }
		    
		    
			//Printing column headers
		    int cols = rsmd.getColumnCount();
		    for (int i=1;i<=cols;i++) {
		    	System.out.format("%-15s",rsmd.getColumnName(i));
	        }
		    System.out.println();
		    
		    //Print fields
		    while (rs.next()) {
		        for (int i = 1; i <= cols; i++) {
		        	String columnValue = rs.getString(i);
		            System.out.format("%-15s",columnValue);    
		        }
		        System.out.println();
		    }
		    System.out.println();
		}
		catch (Exception e) {
			System.out.println("Connection Issue: " + e.getMessage());
		}
	}
}
