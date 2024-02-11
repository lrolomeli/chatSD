package com.distributed.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// Remote interface
interface ChatService extends Remote {
    String sendMessage(String user, String message, String dest, String ipaddr) throws RemoteException;
    boolean isUserRegistered(String user, String password) throws RemoteException;
}

class ChatServiceImpl extends UnicastRemoteObject implements ChatService {

    public ChatServiceImpl() throws RemoteException {
        // Constructor
    }

    @Override
    public String sendMessage(String user, String message, String dest, String ipaddr) throws RemoteException {
        // Simulate some server logic
        // This method is executed we get the message from a client
        // and we know who is the client and who to send the message
        // but there is one thing we don't know,
        // every client connection should be managed 
        // once the client is connected we must store every aspect of 
        // its connection such as ip/user/blahblah/ 
        // a question -> all the clients has to registry its objects in RMI?
        // so that the server can then call every single client?

        // Avoid sending the message back to the sender
        // Locate Registry in the host
        try{
            ChatService chatService = (ChatService) java.rmi.Naming.lookup("rmi://"+ipaddr+"/"+dest);
            // Get the reference of the object in the RMI
  
            chatService.sendMessage(user, message, dest, "localhost");

        
        }catch(Exception e){
            System.out.println("Client side error..." + e);
        }
        return "th";

    }

	public boolean isUserRegistered(String user, String password) throws RemoteException {
		String[] Password = {"123","456"};
		String[] Username = {"Luis", "Alex"};
		
		for(int i=0; i<Username.length; i++) {
			
			if (user.equals(Username[i]) && password.equals(Password[i])) {
				return true;
			}
		}
		return false;
	}

}

public class RMIServer {
    public static void main(String[] args) {
        try {
            ChatService chatService = new ChatServiceImpl();

            // Binding the remote object to the registry
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            java.rmi.Naming.rebind("ChatService", chatService);

            System.out.println("Server is running...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
