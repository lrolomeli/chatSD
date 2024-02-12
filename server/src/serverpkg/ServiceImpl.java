package serverpkg;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import clientpkg.Chat;

public class ServiceImpl extends UnicastRemoteObject implements Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCurrentIpAddress(String user) {
		// Searching for it on DB
		return "localhost";
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
		String[] Password = { "123", "456" };
		String[] Username = { "Luis", "Alex" };
		String decryptPass = Server.decrypt(password);

		for (int i = 0; i < Username.length; i++) {

			if (user.equals(Username[i]) && decryptPass.equals(Password[i])) {
				return true;
			}
		}
		return false;
	}

}
