package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import chatinterface.ChatInterface;

public class Client {
	
	private ChatInterface chatRemObj;
	
	public Client() {

	}
	
	public boolean startClient() {
		String serverAddress = "localhost";
		int serverPort = 50001;
		
		try {
			Registry registry = LocateRegistry.getRegistry(serverAddress, serverPort);
			this.chatRemObj = (ChatInterface) (registry.lookup("chatserver"));
			if(null != this.chatRemObj) {
				return true;
			}
		}catch(Exception e){
			System.out.println("No se encuentra disponible el servidor, intentelo mas tarde...");
			//e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public void stopClient(int userId) {
		try {
			this.chatRemObj.changeStatus(userId, false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean registerUser(String user, String pass) {
		try {
			return this.chatRemObj.registerUser(user, pass);
		} catch (RemoteException e) {
			System.err.println("Hubo un problema de comunicacion, compruebe su conexion.");
			e.printStackTrace();
		}
		return false;
	}
	
	public int authUser(String user, String pass) {
		try {
			return this.chatRemObj.authenticateUser(user, pass);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean sendMessage(String msg, int usr1, int usr2) {
		try {
			return this.chatRemObj.sendMessage(msg,usr1,usr2);

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	
	public String getGroups() {
		try {
			return this.chatRemObj.getGroups();
			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String getUsers() {
		try {
			return this.chatRemObj.userStatus();
			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public boolean changeUserStatus(int userId, boolean st) {
		try {
			return this.chatRemObj.changeStatus(userId, st);
			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public String getMessages(int chatId) {
		try {
			return this.chatRemObj.loadMess(chatId);
			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public int getChatId(int usrid1, int usrid2) {
		try {
			return this.chatRemObj.openChat(usrid1 , usrid2);
			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public int removeUserFromGroup(int chatid, int userid) {
		try {
			return this.chatRemObj.removeUserFromGroup(chatid, userid);
			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public int addUserToGroup(int chatid, int userid) {
		try {
			return this.chatRemObj.addUserToGroup(chatid, userid);
			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public int createGroup(String name, int[] users, int admin) {
		try {
			return this.chatRemObj.createGroup(name, users, admin);
			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public int deleteGroup(int chatid) {
		try {
			return this.chatRemObj.deleteGroup(chatid);
			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public boolean partOfGroup(int chatid, int userid) {
		try {
			return this.chatRemObj.partOfGroup(chatid, userid);
			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public int getAdmin(int chatid) {
		try {
			return this.chatRemObj.getAdmin(chatid);
			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
}
