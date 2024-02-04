import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client{

    public static void main(String[] args){
        try{
            // Locate Registry in the host
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9100);

            // Get the reference of the object in the RMI
            User user = (User) registry.lookup("u");

            // Invoke the remote methods of the referenced objects
            //System.out.println(laptop.getDescription() + "," + laptop.getName()+" for only $" + laptop.getPrice());
            Client.login(user.getUsername() + user.getPassword());
        }catch(Exception e){
            System.out.println("Client side error..." + e);
        }
    }

    public static void login(String up){
        Scanner sc = new Scanner(System.in);
        System.out.print("User: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.println("User: " + username);
        System.out.println("Password: " + password);
        if (up.equals(username+password)){
            System.out.println("Welcome...");
        }
        else{
            System.out.println("Incorrect Password...");
        }
    }

}