package server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.HashMap;



public class MariaDBConnection {
    
    private Connection conn;
    private Map<Integer, Long> lastCheckTimestamps;

    public MariaDBConnection(){
    	this.conn = null;
    	this.lastCheckTimestamps = null;
    }
    
    public void connect() {
        String database = "chatdb";
        String hostname = "localhost";
        String port = "3306";
        String url = "jdbc:mariadb://" + hostname + ":" + port + "/" + database + "?useSSL=false";
        String username = "luislomeli";
        String password = "pelonsun";
        try {
        	this.lastCheckTimestamps = new HashMap<>();
            this.conn = DriverManager.getConnection(url, username, password);
            System.out.println(this.conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	public String readMessages(int chatId) {
		System.out.println("Buscando Mensajes...");
		String msgs="";
        // SQL query to read data into the table
		String sql = "SELECT * FROM message WHERE chat_id=? LIMIT 10";
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
	            msgs += "" + sender_id + ":" + message_text + "\n"; 
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
    
    public String checkForNewMessages(int userId) {
        try {
        	// checa la ultima vez que reviso sus mensajes el usuario
            long lastCheckTimestamp = lastCheckTimestamps.getOrDefault(userId, 0L);

            // Query for new messages since the last check timestamp
            // si los reviso hace 5 minutos el siguiente query debe devolver los mensajes
            // que hayan llegado en estos 5 minutos
            String query = "SELECT * FROM message WHERE sender_id = ? AND timestamp > ?";
            PreparedStatement statement = this.conn.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setTimestamp(2, new Timestamp(lastCheckTimestamp));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Process new messages
                int messageId = resultSet.getInt("message_id");
                String messageContent = resultSet.getString("message_text");
                System.out.println("New message (ID: " + messageId + "): " + messageContent);
                
                return null;
            }

            // La ultima vez que revise si habia mensajes nuevos para mi
            // efectivamente estoy checando si hay mensajes nuevos para este usuario
            // cada cuanto? 30 segundos podria ser 
            lastCheckTimestamps.put(userId, System.currentTimeMillis());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
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
			System.err.println("Ha ocurrido un error probablemente el usuario ya se ha tomado" + e);
			//e.printStackTrace();
			return false;
		}
		return false;

	}
	

	private void insertGroup(String name, int chatid, int admin) {
        String sql = "INSERT INTO chat_group (group_name,chat_id,admin) VALUES (?,?,?)";
    	PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement(sql);
	        // Set values for parameters
			pstmt.setString(1, name);
	    	pstmt.setInt(2, chatid);
	    	pstmt.setInt(3, admin);
	        
	        // Executing the insert statement
	        int rowsAffected = pstmt.executeUpdate();
	        
	        if (rowsAffected == 1) {
	        	System.out.println("New chat ID:");
	        }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        // Checking if the insertion was successful
	}
	
	
	private void insertUserToChat(int userId, int chatId) {
        String sql = "INSERT INTO chat_membership (chat_id,user_id) VALUES (?,?)";
    	PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement(sql);
	        // Set values for parameters
	    	pstmt.setInt(1, chatId);
	    	pstmt.setInt(2, userId);
	        
	        // Executing the insert statement
	        int rowsAffected = pstmt.executeUpdate();
	        
	        if (rowsAffected == 1) {
	        	System.out.println("New chat ID:");
	        }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        // Checking if the insertion was successful
	}
	
	private void addUsersToChat(int[] usr, int chatId) {
		
		System.out.println("Adding users to the chat...");
		
		for(int u:usr) {
			insertUserToChat(u, chatId);	
		}

	}
	
	// nuestra interfaz que crea un nuevo chat inserte una nueva entrada 
	// a la tabla de chats
	private int createChat(String chatName) {
		
		System.out.println("Creating new chat...");
		
        // SQL query to insert data into the table
        String sql = "INSERT INTO chat (chat_name) VALUES (?)";
        PreparedStatement pstmt;
		
        try {
        	pstmt = this.conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        // Set values for parameters
        	pstmt.setString(1, chatName);
	        
            // Executing the insert statement
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("rows affected -> "+rowsAffected);
            // Checking if the insertion was successful
            if (rowsAffected == 1) {
                // Retrieving the generated keys
                ResultSet rs = pstmt.getGeneratedKeys();
                System.out.println("the generatedkeys are -> "+rs);
                if (rs.next()) {
                    // Retrieving the newly created chat ID
                    int newChatId = rs.getInt(1);
                    System.out.println("New chat ID: " + newChatId);
                    return newChatId;
                }
            }
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 0;

	}
	
	private int chatExist(int user1, int user2) {		
		
		// busca si hay un chat creado para estos usuarios
		String sql = "SELECT cm1.chat_id FROM chat_membership cm1 JOIN chat_membership cm2 ON cm1.chat_id = cm2.chat_id WHERE cm1.user_id =? AND cm2.user_id =?";
		PreparedStatement stmt;
		
		// Se busca el chat id que coincida con ambos usuarios y se retorna el ID del chat
		try {
			
			stmt = this.conn.prepareStatement(sql);
			// Set values for parameters
	        stmt.setInt(1, user1);
	        stmt.setInt(2, user2);
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
	
	public int openChat(int usr1, int usr2) {
		int chatid = 0;
		chatid = chatExist(usr1, usr2);
		
		if(0 == chatid) {
			chatid = createChat(""+usr1+","+usr2+"");
			// si no existe el chat debe crearse
			int[] users = new int[2];
			users[0] = usr1;
			users[1] = usr2;
			System.out.println(chatid);
			if(0 != chatid) {
				addUsersToChat(users, chatid);
				return chatid;
			}
			else {
				System.out.println("No se ha podido crear el chat");
				return 0;
			}
		}
		else {
			return chatid;
		}
	}
	
	public int createGroup(String name, int[] users, int admin) {
		
		// 1) Crear nueva entrada en tabla chat
		int chatid = createChat("users:"+users+":admin:"+admin);
		// 2) Crear nueva entrada en tabla grupo, asociada a chat
		insertGroup(name, chatid, admin);
		// 3) addUsers to chat
		insertUserToChat(admin, chatid);
		addUsersToChat(users, chatid);
		
		return chatid;
	}
	
	public boolean insertMessage(String message, int userId, int chatId) {		

		String sql = "INSERT INTO message (sender_id , chat_id,  message_text) VALUES (?,?,?)";
		PreparedStatement stmt;
	
		try {
			
			stmt = this.conn.prepareStatement(sql);
			// Set values for parameters
			stmt.setInt(1, userId);
			stmt.setInt(2, chatId);
			stmt.setString(3, message);
			
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
	
	public String readUsers() {
		System.out.println("Looking for users...");
		String users = "";
		// SQL query to insert data into the table
		String sql = "SELECT id_user,username,status FROM user";
		PreparedStatement stmt;
		try {
			stmt = this.conn.prepareStatement(sql);
			// Set values for parameters
			//stmt.setString(1, user);
			ResultSet rs = stmt.executeQuery();
		
			while (rs.next()) {
				//Display values
				int userId = rs.getInt("id_user");
				String username = rs.getString("username");
				boolean status = rs.getBoolean("status");
				if(status)
					users += "("+userId+") User: " + username + " Status: connnected\n";
				else
					users += "("+userId+") User: " + username + " Status: disconnected\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return users;
	}
	
	public String whatGroups() {
		System.out.println("Looking for groups...");
		String groups = "";
		// SQL query to insert data into the table
		String sql = "SELECT chat_id,group_name FROM chat_group";
		PreparedStatement stmt;
		try {
			stmt = this.conn.prepareStatement(sql);
			// Set values for parameters
			ResultSet rs = stmt.executeQuery();
		
			while (rs.next()) {
				//Display values
				int chatId = rs.getInt("chat_id");
				String groupname = rs.getString("group_name");
				groups += "("+chatId+") Group: " + groupname + "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return groups;
	}
	
	public int readUserId(String user) {

		System.out.println("Buscando ID...");
		
        // SQL query to read data into the table
		String sql = "SELECT id_user FROM user WHERE username = ?";
        PreparedStatement stmt;
		try {
			stmt = this.conn.prepareStatement(sql);
	        // Set values for parameters
	        stmt.setString(1, user);
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            //Display values
	        	int id = rs.getInt("id_user");
	            System.out.print("\nid:" + id  );
	            return id;
	        }
	        //return pass;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 0;
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
	
	public boolean changeStatus(int userId, boolean status) {		

		String sql = "UPDATE user SET status = ? WHERE id_user = ?";
		PreparedStatement stmt;
	
		try {
			
			stmt = this.conn.prepareStatement(sql);
			// Set values for parameters
			stmt.setBoolean(1, status);
			stmt.setInt(2, userId);
			
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

}
