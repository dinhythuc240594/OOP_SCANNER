package School;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class sqlconnection {
	Connection conn = null;
	
	public static Connection dbConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			File dbFile = new File("."); 
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath() + "\\database.db");

//			System.out.println("Connection is successful");
			return conn;
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
