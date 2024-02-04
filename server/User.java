import java.rmi.Remote;
import java.rmi.RemoteException;

public interface User extends Remote {
    // Defining API

    public String getUsername() throws RemoteException;
    public String getPassword() throws RemoteException;
}