package server;

public class Model {

	private MariaDBConnection db;
	private Decrypter aes_d;
	
	public Model() {
		this.db = new MariaDBConnection();
		this.aes_d = new Decrypter();
	}

	public boolean registerUser(String user, String password) {
		return this.db.storeUserInDB(user, password);
	}
	
	public boolean authenticateUser(String user, String password) {
		
		String dbpass = this.db.readPassword(user);
		
		if(null == dbpass) {
			return false;
		}
		else {
			return dbpass.equals(password);
		}

	}
	
	public int openChat(String arg0, String arg1) {
		String firstFiveChars = arg1.substring(0, Math.min(arg1.length(), 5));
		if(firstFiveChars.equals("grupo"))
			return this.db.openChatGroup(arg0, arg1);
		else
			return this.db.openChat(arg0, arg1);
	}
	
	public boolean sendMessage(String msg, String sender, int chatId) {
		// TODO: 
		return this.db.insertMessage(sender, chatId, msg);
	}
	
	public String userStatus() {
		// TODO:
		return null;
	}
	
	public String loadMess(int chat) {
		return this.db.readMessages(chat);
	}
	
	public boolean goOffline(String arg0) {
		// TODO:
		return false;
	}
	
	public String decryptText(String text) {
        return this.aes_d.decrypt(text);
	}
	
	public void connectDB() {
		this.db.connect();
	}
	
}
