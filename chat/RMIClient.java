import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
//import AESCS.AESCS;

// Remote interface
interface ChatService extends Remote {
    String sendMessage(String user, String message, String dest) throws RemoteException;
    void registerClient(String clientName, ChatService client) throws RemoteException;
    void unregisterClient(String clientName) throws RemoteException;
}

class ChatClient extends UnicastRemoteObject implements ChatService {

    public ChatClient() throws RemoteException {
    }

    @Override
    public String sendMessage(String user, String message, String dest) throws RemoteException {
        // This is called by other clients through the server when they send a message
        try{
            Client client = new Client();
            client.initFromStrings("CHuO1Fjd8YgJqTyapibFBQ==","e3IYYJC2hxe24/EO");
            String decryptedMessage = client.decrypt(message);
            System.out.println("\n"+ user+": " + decryptedMessage);
        }catch(Exception e){

        }
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

                Server server = new Server();
                server.initFromStrings("CHuO1Fjd8YgJqTyapibFBQ==", "e3IYYJC2hxe24/EO");

                // after the cast we can call it's methods
                client.registerClient(user, client);
                System.out.print("Type: ");
                String msg = input.nextLine();
                String encryptedMessage = server.encrypt(msg);
                do{
                    String reply = client.sendMessage(user, encryptedMessage, dest);
                    System.out.print("Type: ");
                    msg = input.nextLine();
                    encryptedMessage = server.encrypt(msg);
                }while(!msg.equals("bye"));
                input.close();
                client.unregisterClient(user);

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
