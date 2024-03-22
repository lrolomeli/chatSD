package server;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import chatinterface.ChatInterface;

public class ChatImpl extends UnicastRemoteObject implements ChatInterface{
	
	private static final long serialVersionUID = -1749840874808291602L;
	private Model m;
	
	public ChatImpl() throws RemoteException {
		m = new Model();
		m.connectDB();
	}
	
	@Override
	public boolean authenticateUser(String arg0, String arg1) throws RemoteException {
		return this.m.authenticateUser(arg0, arg1);
	}

	@Override
	public String loadMess(int arg0) throws RemoteException {
		return this.m.loadMess(arg0);
	}

	@Override
	public int openChat(String arg0, String arg1) throws RemoteException {
		return this.m.openChat(arg0, arg1);
	}

	@Override
	public boolean registerUser(String arg0, String arg1) throws RemoteException {
		return this.m.registerUser(arg0, arg1);
	}

	@Override
	public boolean sendMessage(String arg0, String arg1, int arg2) throws RemoteException {
		return this.m.sendMessage(arg0, arg1, arg2);
	}

	@Override
	public String userStatus() throws RemoteException {
		return this.m.userStatus();
	}

	@Override
	public boolean goOffline(String arg0) throws RemoteException {
		return this.m.goOffline(arg0);
	}
}
