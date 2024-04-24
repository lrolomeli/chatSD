package client;

import javax.swing.JFrame;

import client.view.ChatFrame;
import client.view.RegLogFrame;

public class Chat {
	
	private Client c;
	private AES aes;
	private int cchatid; //current chat ID
	private String user;
	private int userId;
	private JFrame frame;
	
	public Chat() {
		this.c = new Client();
		this.aes = new AES();
	}
	
	public boolean connectApp() {
		return this.c.startClient();
	}
	
	public void register(String user, String pass) {
        if(this.c.registerUser(user, this.aes.encrypt(pass))) {
        	this.user = user;
        }
        else
        {
        	// Mandar mensaje a imprimir a la vista
        	System.err.println("Ese usuario ya se ha registrado");
        }
		
	}
	
	public void login(String user, String pass) {
		this.userId = this.c.authUser(user, this.aes.encrypt(pass));
        if(0 == this.userId) {
        	// Mandar mensaje a imprimir a la vista
        	System.out.println("Contraseña incorrecta");
        }
        else {
        	this.frame.dispose();
        	this.frame = null;
        	this.frame = new ChatFrame();
        }
		
	}
	
	public void setCurrentChatId(int who) {
		this.cchatid = this.c.getChatId(this.userId, who);
		
	}
	
	public void sendMsgTo(String msg) {		

		this.c.sendMessage(this.aes.encrypt(msg), this.userId, this.cchatid);

	}

	public void getGroups() {
		String groups = this.c.getGroups();
		System.out.println(groups);
	}
	
	public void getUsers() {
		String users = this.c.getUsers();
		System.out.println(users);
	}
	
	public int get_cChatId() {
		return this.cchatid;
	}
	
	public void changeStatus(int id, boolean status) {
		this.c.changeUserStatus(id, status);
	}
	
	public String getUser() {
		return this.user;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public void getMessages() {
		int i = 0;
		String msg = this.c.getMessages(this.cchatid);
		String[] rows = msg.split("\n");
		String areaMsg = "";
		for (String r : rows) {
			String[] messages = r.split(":");
			for (String m : messages){
				if(i==0) {
					areaMsg += ("sender: " + m);
					System.out.println("sender: "+m);
					i++;
				}
				else {
					areaMsg += (this.aes.decrypt(m));
					System.out.println(this.aes.decrypt(m));
					i=0;
				}
			}
		}
		System.out.println(areaMsg);
			
	}
	
	public void createGroup(String name, int[] userlist) {
		this.cchatid = this.c.createGroup(name, userlist, this.userId);
		System.out.println("chatid: "+this.cchatid);
	}
	
	public void goodbye() {
		changeStatus(this.userId,false);	
	}
	
	public void startApp() {
		this.frame = new RegLogFrame(this);

	}
	
}
