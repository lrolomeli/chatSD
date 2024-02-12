package clientpkg;

import java.rmi.Remote;

public interface Chat extends Remote {
	
    String getMessage(String message, String sender) throws Exception;
    boolean isUserAvailable() throws Exception;
    
}
