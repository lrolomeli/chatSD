import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

// Remote interface
interface ChatService extends Remote {
    String sendMessage(String message, String sender) throws RemoteException;
    void registerClient(String clientName, ChatService client) throws RemoteException;
    void unregisterClient(String clientName) throws RemoteException;
}

class ChatClient extends UnicastRemoteObject implements ChatService {

    public ChatClient() throws RemoteException {
    }

    @Override
    public String sendMessage(String message, String sender) throws RemoteException {
        // This is called by other clients through the server when they send a message
        System.out.println("Received message: " + message);
        return null;
    }

    // Method to register a client with the server
    @Override
    public void registerClient(String clientName, ChatService client) {
    }

    // Method to unregister a client from the server
    @Override
    public void unregisterClient(String clientName) {
    }

}

public class RMIClient{

    public static void main(String[] args) {
            try {
                // from client side we are looking the api provided by the server
                // by searching for server host ip address and api service called ChatService
                ChatService client = (ChatService) java.rmi.Naming.lookup("rmi://localhost/ChatService");
                // after founding the api or the object in rmi registry we may cast it into
                // a ChatService object so we can use it as local object.

                // Simulate client interaction
                Scanner input = new Scanner(System.in);
                System.out.print("User: ");
                String user = input.nextLine();

                System.out.print("Who you want to talk to: ");
                String dest = input.nextLine();

                ChatClient chatClient = new ChatClient();
                java.rmi.Naming.rebind(user, chatClient);

                // after the cast we can call it's methods
                client.registerClient(user, client);
                System.out.print("Type: ");
                String msg = input.nextLine();
                do{
                    String reply = client.sendMessage(user + ": " + msg, dest);
                    System.out.print("Type: ");
                    msg = input.nextLine();
                }while(!msg.equals("bye"));
                client.unregisterClient(user);

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
