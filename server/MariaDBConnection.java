package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MariaDBConnection {

    private final String database = "chatdb";
    private final String hostname = "localhost";
    private final String port = "3306";
    private final String url = "jdbc:mariadb://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    private final String username = "luisrlp";
    private final String password = "Continental2.";
    
    private Connection conn;

    public MariaDBConnection(){
    	this.conn = null;
    }
    
    public void connect() {
        try {
            this.conn = DriverManager.getConnection(url, username, password);
            System.out.println(this.conn);
        } catch (SQLException e) {
        	System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }
    
	public boolean storeUserInDB(String user, String password) {
		
		System.out.println("Uploading User...");
		
        // SQL query to insert data into the table
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
        PreparedStatement preparedStatement;
		
        try {
			preparedStatement = this.conn.prepareStatement(sql);
	        // Set values for parameters
	        preparedStatement.setString(1, user);
	        preparedStatement.setString(2, password);
	        // Execute the query
	        int rowsInserted = preparedStatement.executeUpdate();
	        if (rowsInserted > 0) {
	        	System.out.println("A new user was inserted successfully!");
	            return true;
	        }
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;

	}
	
	public String readPassword(String user) {
		System.out.println("Comparing passwords...");
		
        // SQL query to insert data into the table
        String sql = "SELECT password FROM user WHERE username=?";
        PreparedStatement stmt;
		try {
			stmt = this.conn.prepareStatement(sql);
	        // Set values for parameters
	        stmt.setString(1, user);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            //Display values
	        	String pass = rs.getString("password");
	            System.out.print("Password: " + pass);
	            return pass;
	        }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return null;
	}

}
