package clientpkg;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatImpl extends UnicastRemoteObject implements Chat {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChatImpl() throws RemoteException {
	}

	@Override
	public String getMessage(String message, String sender) throws Exception {
		// This is called by other clients through the server when they send a message
		String decryptedMessage = Client.decrypt(message);
		System.out.println("\n" + sender + ": " + decryptedMessage);
		return "success";
	}

	@Override
	public boolean isUserAvailable() throws Exception {
		return true;
	}

}
