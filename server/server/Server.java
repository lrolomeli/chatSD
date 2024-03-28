package server;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import chatinterface.ChatInterface;


public class Server {
	
	public static void main(String[] args) {
		
		try {
			int PORT = 50001;
			ChatImpl ci = new ChatImpl();
			
			String ipAddress = InetAddress.getLocalHost().toString();
			System.out.println("Listening to port: "+PORT+"\nOn Address: "+ipAddress+"\n");
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.bind("chatserver", (ChatInterface)ci);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
