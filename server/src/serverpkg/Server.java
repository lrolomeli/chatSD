package serverpkg;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

	public static String decrypt(String msg) throws Exception {
		
		Decrypt d = new Decrypt();
        d.initFromStrings("CHuO1Fjd8YgJqTyapibFBQ==","e3IYYJC2hxe24/EO");
        return d.decrypt(msg);
	}
	
	public static void main(String [] args) throws Exception {
		ServiceImpl service = new ServiceImpl();
		String rmiService = "rmi://localhost/Service";
		LocateRegistry.createRegistry(1099);
		Naming.rebind(rmiService, service);
		System.out.println("Server running...");
	}
	
}