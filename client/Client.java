package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import chatinterface.ChatInterface;

public class Client {
	
	private ChatInterface chatRemObj;
	
	public Client() {
		String serverAddress = "localhost";
		int serverPort = 50001;
		
		try {
			Registry registry = LocateRegistry.getRegistry(serverAddress, serverPort);
			this.chatRemObj = (ChatInterface) (registry.lookup("chatserver"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void runClient() {
		

	}
	
	public void stopClient(String user) {
		try {
			this.chatRemObj.goOffline(user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean registerUser(String user, String pass) {
		try {
			return this.chatRemObj.registerUser(user, pass);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean authUser(String user, String pass) {
		try {
			return this.chatRemObj.authenticateUser(user, pass);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean sendMessage(String arg0, String arg1, String arg2) {
		try {
			return this.chatRemObj.sendMessage("Mensaje","user1","user2");

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return false;
	}
	
}
