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
		// TODO: 
		return 0;
	}
	
	public boolean sendMessage(String arg0, String arg1, String arg2) {
		// TODO: 
		return false;
	}
	
	public String userStatus() {
		// TODO:
		return null;
	}
	
	public String loadMess() {
		// TODO:
		return null;
	}
	
	public int createChat(String arg0, String arg1) {
		// TODO:
		return 0;
	}
	
	public boolean goOffline(String arg0) {
		// TODO:
		return false;
	}
	
	public String decryptText(String text) {
        return aes_d.decrypt(text);
	}
	
	public void connectDB() {
		this.db.connect();
	}
	
}
