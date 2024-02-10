import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.io.Console;
//import AESCS.AESCS;

// Remote interface
interface ChatService extends Remote {
    String sendMessage(String user, String message, String dest, String ipaddr) throws RemoteException;
    boolean isUserRegistered(String user, String password) throws RemoteException;
}

class ChatClient extends UnicastRemoteObject implements ChatService {

    public ChatClient() throws RemoteException {
    }

    @Override
    public String sendMessage(String user, String message, String dest, String ipaddr) throws RemoteException {
        // This is called by other clients through the server when they send a message
        try{
            AESClient client = new AESClient();
            client.initFromStrings("CHuO1Fjd8YgJqTyapibFBQ==","e3IYYJC2hxe24/EO");
            String decryptedMessage = client.decrypt(message);
            System.out.println("\n"+ user+": " + decryptedMessage);
        }catch(Exception e){

        }
        return null;
    }

    @Override
    public boolean isUserRegistered(String user, String password) throws RemoteException {
        return true;
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
                Console console = System.console();
                Scanner input = new Scanner(System.in);
                System.out.print("User: ");
                String user = input.nextLine();

                char[] passwordArray = console.readPassword("Enter your password: ");
                String pass = new String(passwordArray);

                ChatClient chatClient = new ChatClient();
                java.rmi.Naming.rebind(user, chatClient);
                
                // after the cast we can call it's methods
                if(client.isUserRegistered(user, pass)) {
                    System.out.println("Access Granted! Welcome " + user);
                }
                else {
                    System.out.println("I'm sorry thats an invalid Username or Password!");
                }
                java.util.Arrays.fill(passwordArray, ' ');
                System.out.print("Who you want to talk to: ");
                String dest = input.nextLine();

                AESServer server = new AESServer();
                server.initFromStrings("CHuO1Fjd8YgJqTyapibFBQ==", "e3IYYJC2hxe24/EO");
                
                System.out.print("Enter a Msg: ");
                String msg = input.nextLine();
                String encryptedMessage = server.encrypt(msg);

                do{

                    client.sendMessage(user, encryptedMessage, dest, "localhost");
                    System.out.print("Enter a Msg: ");
                    msg = input.nextLine();
                    encryptedMessage = server.encrypt(msg);

                }while(!msg.equals("bye"));
                input.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
