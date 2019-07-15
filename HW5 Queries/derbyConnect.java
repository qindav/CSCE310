import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class derbyConnect {
	public static void main(String[] args) {
		Connection conn = null;
		
		try {
			//Connect
			String connectionString = "jdbc:derby:C:/Apache/db-derby-10.14.2.0-bin/bin/shipsDB";
			Class.forName ("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			conn = DriverManager.getConnection(connectionString, "", "");
			System.out.println ("Database connection established");
			
			//Gather class info
			InputStreamReader in = new InputStreamReader(System.in);
		    BufferedReader keyboard = new BufferedReader(in);
		    System.out.println("Enter the class name: ");
		    String shipclass = keyboard.readLine();
		    System.out.println("Enter the type: ");
		    String shiptype = keyboard.readLine();
		    System.out.println("Enter the country: ");
		    String shipcountry = keyboard.readLine();
		    System.out.println("Enter the # of guns: ");
		    int shipnumGuns = Integer.parseInt(keyboard.readLine());
		    System.out.println("Enter the bore: ");
		    int shipbore = Integer.parseInt(keyboard.readLine());
		    System.out.println("Enter the disp: ");
		    int shipdisp = Integer.parseInt(keyboard.readLine());
		    
		    //Insert the new class
		    Statement stmt = conn.createStatement();
			stmt.executeUpdate(""
					+ "INSERT INTO classes (class,type,country,numGuns,bore,disp) "
					+ "VALUES ('" + shipclass + "','"
					+ shiptype + "','"
					+ shipcountry + "',"
					+ shipnumGuns + ","
					+ shipbore + ","
					+ shipdisp + ")");
			
			
		    String[] ships;
		    //Gather ships data from user
		    System.out.println("Now in ship entering mode, input shipname = q to stop.");
			while(true) {
			    System.out.println("Enter a new ship name: ");
			    String shipname = keyboard.readLine();	
			    if(shipname.equals("q")) {
			    	break;
			    }
			    System.out.println("Enter the launch year: ");
			    int launchYear = Integer.parseInt(keyboard.readLine());
			    
			    
			   stmt.executeUpdate(""
			    		+"INSERT INTO ships (name, class, launched) VALUES ("
			    		+ "'"
			    		+ shipname
			    		+ "','"
			    		+ shipclass
			    		+ "',"
			    		+ launchYear
			    		+ ")");
		    }
			
		}
		catch (Exception e) {
			System.out.println("Connection Issue: " + e.getMessage());
		}
	}
}
