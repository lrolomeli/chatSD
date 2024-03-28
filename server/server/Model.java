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
		// busca si hay un chat creado para estos usuarios
		int chatid = this.db.createChat(""+arg0+","+arg1+"");
		// si lo hay regresa un id diferente a cero.
		if(0 != chatid) {
			// Dado que el chat existe cargar los mensajes del chat
			return chatid;
			
		}
		else
		{
			// si no existe el chat debe crearse
			int[] users = new int[2];
			users[0] = arg0;
			users[1] = arg1;
			System.out.println(chatid);
			if(0 != chatid) {
				this.db.addUsersToChat(users, chatid);
				return chatid;
			}
			else {
				System.out.println("No se ha podido crear el chat, consulte a su medico");
				return 0;
			}
		}

	}
	

	public int createGroup(String name, int[] users, int admin) {
		return this.db.createGroup(name, users, admin);
	}
	
	public boolean sendMessage(String msg, int sender, int chatId) {
		return this.db.insertMessage(msg, sender, chatId);
	}
	
	public String userStatus() {
		return this.db.readUsers();
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
