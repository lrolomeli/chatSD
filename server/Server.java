import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server {
    public static void main(String[] args){
        try{
            // Set hostname for the server
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            // Lets create product objects
            UserImpl user = new UserImpl("Luis", "123456");
            
            // Export object using Unicast
            User stub = (User) UnicastRemoteObject.exportObject(user, 0);

            // Register the exported object or object in RMI registry
            // So the client can see it
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9100);
            registry.rebind("u", stub);

        }catch(Exception e){
            System.out.println("Some server error..." + e);
        }
    }
}