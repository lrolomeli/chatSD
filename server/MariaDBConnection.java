package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MariaDBConnection {
    
    private Connection conn;

    public MariaDBConnection(){
    	this.conn = null;
    }
    
    public void connect() {
        String database = "chatdb";
        String hostname = "localhost";
        String port = "3306";
        String url = "jdbc:mariadb://" + hostname + ":" + port + "/" + database + "?useSSL=false";
        String username = "luisrlp";
        String password = "Continental2.";
        try {
            this.conn = DriverManager.getConnection(url, username, password);
            System.out.println(this.conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
	
	
	public int openChatGroup(String user, String chat_name) {

		String sql = "SELECT chat_id FROM chat WHERE chat_name=?";
		PreparedStatement stmt;
		
		// Buscaremos si existe un chat_id asociado a ese nombre
		// En caso de existir veremos si el usuario ya se encuentra registrado en el grupo
		// Si esta registrado devolvemos el chat_id
		// Si no esta registrado lo registramos y devolvemos el chat_id
		// si existe el chat debemos crear el chat y agregar ese usuario al grupo
	
		try {
			
			stmt = this.conn.prepareStatement(sql);
			// Set values for parameters
			stmt.setString(2, chat_name);
			
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            //Display values
	        	return rs.getInt("chat_id");   
	        }
	    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	
	public int openChat(String user1, String user2) {		
		
		String getUserId1 = "SELECT user_id FROM user WHERE username="+user1;
		String getUserId2 = "SELECT user_id FROM user WHERE username="+user2;
		String sql = "SELECT cm1.chat_id FROM chat_membership cm1 JOIN chat_membership cm2 ON cm1.chat_id = cm2.chat_id WHERE cm1.user_id ="+getUserId1+" AND cm2.user_id ="+getUserId2;
		PreparedStatement stmt;
		
		// podemos buscar si el usuario2 esta ligado a algun chat donde tambien esta el usuario1
		// pero tambien podriamos buscar si el chat que el usuario1 quiere abrir existe
		// y por ejemplo si el usuario1 quiere abrir el chat usuario2
		// y el usuario2 quiere abrir el chat usuario1 van a abrir chats diferentes
		// por eso es importante registrar ambos usuarios a un chat
		// para eso primero importante es dar de alta el chat con su nombre que en este 
		// caso tomara el nombre de ambos usuarios y despues se agregara a la tabla chat_membership
		// agregando al usuario1 y el chat_id correspondiente
		// al igual que al usuario 2 con el mismo chat_id.
	
		try {
			
			stmt = this.conn.prepareStatement(sql);
			// Set values for parameters
			
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            //Display values
	        	return rs.getInt("chat_id");   
	        }
	    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	
	public boolean insertMessage(String user, int chatId, String message) {		
		
		String getUserId = "SELECT user_id FROM user WHERE username="+user;
		String sql = "INSERT INTO message (sender_id , chat_id,  message_text) VALUES ("+getUserId+",?,?)";
		PreparedStatement stmt;
	
		try {
			
			stmt = this.conn.prepareStatement(sql);
			// Set values for parameters
			stmt.setInt(1, chatId);
			stmt.setString(2, message);
			
			// Execute the query
			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Message success!");
				return true;
			}
	    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public String readMessages(int chatId) {
		System.out.println("Buscando Mensajes...");
		String msgs="";
        // SQL query to read data into the table
		String sql = "SELECT * FROM message WHERE chat_id=? ORDER BY timestamp ASC";
        PreparedStatement stmt;
		try {
			stmt = this.conn.prepareStatement(sql);
	        // Set values for parameters
			stmt.setInt(1, chatId);
			
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            //Display values
	        	String sender_id = rs.getString("sender_id");
	        	String message_text = rs.getString("message_text");
	            msgs += "\n" + sender_id + ":" + message_text;   
	        }
	        System.out.println(msgs);
	        //return pass;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return msgs;
	}
	
	public String readUsers() {
		System.out.println("Looking for users...");
		String users = "";
		// SQL query to insert data into the table
		String sql = "SELECT username,status FROM user";
		PreparedStatement stmt;
		try {
			stmt = this.conn.prepareStatement(sql);
			// Set values for parameters
			//stmt.setString(1, user);
			ResultSet rs = stmt.executeQuery();
		
			while (rs.next()) {
				//Display values
				String username = rs.getString("username");
				boolean status = rs.getBoolean("status");
				if(status)
					users += "User: " + username + "disconnected";
				else
					users += "User: " + username + "connected";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return users;
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
