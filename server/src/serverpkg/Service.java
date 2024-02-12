package serverpkg;

import java.rmi.Remote;

public interface Service extends Remote {

    String sendMessage(String user, String message, String dest) throws Exception;
    boolean authentication(String user, String password) throws Exception;
}
