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
		int chatid = this.db.openChat(arg0, arg1);
		// si lo hay regresa un id diferente a cero.
		if(0 != chatid) {
			// Dado que el chat existe cargar los mensajes del chat
			return chatid;
			
		}
		else
		{
			// si no existe el chat debe crearse
			String u1u2 = String.valueOf(arg0)+String.valueOf(arg1);
			chatid = this.db.createChat(u1u2);
			System.out.println(chatid);
			if(0 != chatid) {
				this.db.addUsersToChat(arg0, arg1, chatid);
				return chatid;
			}
			else {
				System.out.println("No se ha podido crear el chat, consulte a su medico");
				return 0;
			}
		}

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
