package serverpkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLConnection {

    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String database = "chatdb";
    private final String hostname = "localhost";
    private final String port = "3306";
    private final String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    private final String username = "root";
    private final String password = "098651";
    
    private Connection conn;

    public MySQLConnection(){
    	this.conn = null;
    }
    
    public Connection connectMySQL() {

        try {
            Class.forName(driver);
            this.conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return this.conn;
    }
    
	public void storeUserInDB(String user, String password) throws SQLException {
		System.out.println("Succeed "+ this.conn);
		
        // SQL query to insert data into the table
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
        PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
        
        // Set values for parameters
        preparedStatement.setString(1, user);
        preparedStatement.setString(2, password);
        
        // Execute the query
        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
        }
	}

}
