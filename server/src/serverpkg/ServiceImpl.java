package serverpkg;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import clientpkg.Chat;

public class ServiceImpl extends UnicastRemoteObject implements Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MySQLConnection c;

	protected ServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCurrentIpAddress(String user) {
		// Searching for it on DB
		return "localhost";
	}
	
	public void connectDatabase() {
		this.c = new MySQLConnection();
		this.c.connectMySQL();
	}
	
	@Override
	public String sendMessage(String user, String message, String dest) throws Exception {
		try {
			String currentIP = getCurrentIpAddress(dest);
			Chat chat = (Chat) java.rmi.Naming.lookup("rmi://" + currentIP + "/Chat" + dest);
			// Get the reference of the object in the RMI
			chat.getMessage(message, user);
		} catch (Exception e) {
			System.out.println("Client side error..." + e);
		}
		return null;
	}

	@Override
	public boolean authentication(String user, String password) throws Exception {

		String decryptPass = Server.decrypt(password);
		
		String dbPassword = this.c.readPassword(user);
		String decryptedDBPassword = Server.decrypt(dbPassword);
		
		if(decryptedDBPassword.equals(decryptPass)) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public boolean registerUser(String user, String password) throws Exception {
		return this.c.storeUserInDB(user, password);
	}

}
