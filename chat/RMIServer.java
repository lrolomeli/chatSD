import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.rmi.registry.LocateRegistry;

// Remote interface
interface ChatService extends Remote {
    String sendMessage(String message, String sender) throws RemoteException;
    void registerClient(String clientName, ChatService client) throws RemoteException;
    void unregisterClient(String clientName) throws RemoteException;
}

class ChatServiceImpl extends UnicastRemoteObject implements ChatService {
    private Map<String, ChatService> connectedClients = new HashMap<>();

    public ChatServiceImpl() throws RemoteException {
        // Constructor
    }

    @Override
    public String sendMessage(String message, String sender) throws RemoteException {
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
            ChatService chatService = (ChatService) java.rmi.Naming.lookup("rmi://localhost/"+sender);
            // Get the reference of the object in the RMI
  
            chatService.sendMessage(message, sender);

        
        }catch(Exception e){
            System.out.println("Client side error..." + e);
        }
        return "th";

    }

    // Method to register a client with the server
    @Override
    public void registerClient(String clientName, ChatService client) {
        connectedClients.put(clientName, client);
        System.out.println("Client '" + clientName + "' connected.");
    }

    // Method to unregister a client from the server
    @Override
    public void unregisterClient(String clientName) {
        connectedClients.remove(clientName);
        System.out.println("Client '" + clientName + "' disconnected.");
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
