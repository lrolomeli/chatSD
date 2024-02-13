package clientpkg;

import java.io.Console;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import serverpkg.Service;

public class Client {
	
	private Service service = null;
	
	public Client() throws MalformedURLException, RemoteException, NotBoundException {
		String rmiService = "rmi://localhost/Service";
		this.service = (Service) Naming.lookup(rmiService);
	}
	
	public Service getService() {
		return this.service;
	}
	
	public static String encrypt(String msg) throws Exception {
		
        Encrypt e = new Encrypt();
        e.initFromStrings("CHuO1Fjd8YgJqTyapibFBQ==","e3IYYJC2hxe24/EO");
        return e.encrypt(msg);
	}
	
	public static String decrypt(String msg) throws Exception {
		
		Decrypt d = new Decrypt();
        d.initFromStrings("CHuO1Fjd8YgJqTyapibFBQ==","e3IYYJC2hxe24/EO");
        return d.decrypt(msg);
	}
	
	public String getUser(Scanner in) {
		String user;
		// Pedir usuario por consola,
        System.out.print("User: ");
        user = in.nextLine();
        return user;
	}
	
	public String getPassword(Scanner in) throws Exception {
		String pass;
		// Pedir contraseña por consola,
        Console console = System.console();

        if (console != null) {
            char[] passwordArray = console.readPassword("Enter password: ");
            pass = new String(passwordArray);
        } else {
            System.out.print("Password: ");
            pass = in.nextLine();
        }
        
        return Client.encrypt(pass);
	}
	

	public String register(Scanner in) throws Exception {
		String user, pass;
		
        user = getUser(in);
        pass = getPassword(in);
		
        if(this.service.registerUser(user, pass)) {
        	return user;
        }
        else
        {
            return null;
        }
		
	}
	
	public String login(Scanner in) throws Exception {
		String user, pass;
		
        user = getUser(in);
        pass = getPassword(in);
		
        if(this.service.authentication(user, pass)) {
        	return user;
        }
        else
        {
            return null;
        }
		
	}
	
	public String askForMsg(Scanner in) throws Exception {

        System.out.print("Enter a Msg: ");
        String msg = in.nextLine();
        if(msg.equals("q")) {
        	return null;
        }
        return Client.encrypt(msg);

	}
	
	public String whoToTalk(Scanner in) {

        System.out.print("Who you want to talk to: ");
        String dest = in.nextLine();
        
        // if User is not registered in the database return null;
        
		return dest;
	}
	
	public String getIpAddress() {
		return "localhost";
	}
	
	public void registerMethods(String user, String ipaddr) throws RemoteException, MalformedURLException {
        ChatImpl chat = new ChatImpl();
		String rmiService = "rmi://" + ipaddr + "/Chat" + user;
		Naming.rebind(rmiService, chat);
	}
	
	public void deregisterMethods(String user, String ipaddr) throws RemoteException, MalformedURLException, NotBoundException {
		String rmiService = "rmi://" + ipaddr + "/Chat" + user;
		Naming.unbind(rmiService);
	}

	public static void main(String [] args) throws Exception 
	{
		
		Client client = new Client();
		Service service = client.getService();
		Scanner input = new Scanner(System.in);
		String action;
		String user, msg, dest, ipaddr;
		
		if(null != service) 
		{
			
			System.out.println("1) Login\n2) Register\nX) Exit");
			action = input.nextLine();
			if(action.equals("1")) {
				user = client.login(input);
				
				if(null != user) {
					
					ipaddr = client.getIpAddress();
					client.registerMethods(user,ipaddr);
					//avisar al servidor que estas conectado
					
					do {
						dest = client.whoToTalk(input);
						//revisar si el usuario al que deseas hablar esta conectado
						//isUserConnected(dest); //checa en la base de datos
						//en el campo de status del usuario
						
						do {
							msg = client.askForMsg(input);
							if(null != msg)	{
								service.sendMessage(user, msg, dest);
							}
							else {
								System.out.println("You've left the conversation");
							}
							
						} while(null != msg);
							
					}while(null != dest);
					//avisar al servidor
					
					// desconectar al usuario
					client.deregisterMethods(user,ipaddr);
					
				}
			}
			else if(action.equals("2")) {
				
			}
			else {
				
			}				
				
		}
		
		else {
			System.out.println("Server not available");
		}

	}
	
}
