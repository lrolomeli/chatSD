package database;

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
	
	public static void main(String []args) throws SQLException{
		MySQLConnection c = new MySQLConnection();
		Connection conn = c.connectMySQL();
		System.out.println("Succeed "+ c);
		String user = "Luis";
		String password = "123";
		
        // SQL query to insert data into the table
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        
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
