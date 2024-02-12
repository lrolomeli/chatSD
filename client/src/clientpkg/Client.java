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
	
	public String login(Scanner in) throws Exception {
		// Pedir usuario por consola,
        System.out.print("User: ");
        String user = in.nextLine();
		// Pedir contraseña por consola,
        Console console = System.console();
        String pass;
        if (console != null) {
            char[] passwordArray = console.readPassword("Enter password: ");
            pass = new String(passwordArray);
        } else {
            System.out.print("Password: ");
            pass = in.nextLine();
        }
        
        pass = Client.encrypt(pass);
        
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
        return Client.encrypt(msg);

	}
	
	public String whoToTalk(Scanner in) {

        System.out.print("Who you want to talk to: ");
        String dest = in.nextLine();
        
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

	public static void main(String [] args) throws Exception {
		
		Client c = new Client();
		Service s = c.getService();
		Scanner input = new Scanner(System.in);
		String user, msg, dest, ipaddr;
		
		if(null != s) {
			user = c.login(input);
			
			if(null != user) {
				ipaddr = c.getIpAddress();
				c.registerMethods(user,ipaddr);
				//avisar al servidor que estas conectado
				dest = c.whoToTalk(input);
				msg = c.askForMsg(input);
				s.sendMessage(user, msg, dest);
			}
		}
		
		

	}
	
}
