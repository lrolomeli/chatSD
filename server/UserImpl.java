import java.rmi.RemoteException;

public class UserImpl implements User {
    public String username;
    public String password;

    public UserImpl(String newUsername, String newPassword) throws RemoteException{
        this.username = newUsername;
        this.password = newPassword;
    }

    public String getUsername() throws RemoteException{
        return this.username;
    }
    
    public String getPassword() throws RemoteException{
        return this.password;
    }

}