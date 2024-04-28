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
	
	public int authenticateUser(String user, String password) {
		// TODO: complete encryption
		String dbpass = this.db.readPassword(user);
		
		if(null == dbpass) {
			return 0;
		}
		else if(decryptText(dbpass).equals(decryptText(password))){
			return this.db.readUserId(user);
		}
		else
		{
			return 0;
		}
	}
	
	public int openChat(int arg0, int arg1) {
		return this.db.openChat(arg0, arg1);
	}

	public int createGroup(String name, int[] users, int admin) {
		return this.db.createGroup(name, users, admin);
	}
	
	public int deleteGroup(int chatid) {
		return this.db.deleteGroup(chatid);
	}
	
	public int removeUserFromGroup(int chatid, int userid) {
		return this.db.removeUserFromGroup(chatid, userid);
	}
	
	public int getAdmin(int chatid) {
		return this.db.getAdmin(chatid);
	}
	
	public boolean partOfGroup(int chatid, int userid) {
		return this.db.partOfGroup(chatid, userid);
	}
	
	public int addUserToGroup(int chatid, int userid) {
		return this.db.addUserToGroup(chatid, userid);
	}
	
	public boolean sendMessage(String msg, int sender, int chatId) {
		return this.db.insertMessage(msg, sender, chatId);
	}
	
	public String userStatus() {
		return this.db.readUsers();
	}
	
	public String getGroups() {
		return this.db.whatGroups();
	}
	
	public String loadMess(int chat) {
		return this.db.readMessages(chat);
	}
	
	public boolean changeStatus(int arg0, boolean status) {
		return this.db.changeStatus(arg0,status);
	}
	
	public String decryptText(String text) {
        return this.aes_d.decrypt(text);
	}
	
	public void connectDB() {
		this.db.connect();
	}
	
}
