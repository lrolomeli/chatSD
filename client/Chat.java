package client;

public class Chat {
	
	private Client c;
	private View v;
	private String user;
	private int userId;
	
	public Chat() {
		this.c = new Client();
		this.v = new View();
	}
	
	public boolean connectApp() {
		return this.c.startClient();
	}
	
	public String register() {
		String user, pass;
		System.out.println("Registrate");
		System.out.println("Si ya tienes cuenta presiona x + Enter");
        user = this.v.getUser();
        if(!(user.equals("x"))) {
        	pass = this.v.getPassword();	
            if(this.c.registerUser(user, pass)) {
            	return user;
            }
            else
            {
            	System.err.println("Ese usuario ya se ha registrado");
                return null;
            }
        }
        else
        {
        	return null;
        }

		
	}
	
	public boolean login() {
		String pass;
		System.out.println("Cual es tu cuenta");
        this.user = this.v.getUser();
        pass = this.v.getPassword();
		this.userId = this.c.authUser(user, pass);
        if(0 != this.userId) {
        	System.out.println("Bienvenido "+this.user);
        	return true;
        }
        else {
        	return false;
        }
		
	}
	
	public void sendMsgTo() {
		String msg;
		int who;
		who = this.v.whoToTalk();
		int chatid;
		chatid = this.c.getChatId(this.userId,who);
		msg = this.v.askForMsg();
		System.out.println("receptor "+who);
		System.out.println("chatid "+chatid);
		System.out.println("transmisor "+this.userId);
		this.c.sendMessage(msg, this.userId, chatid);
	}
	
	
	public void getUsers() {
		String users = this.c.getUsers();
		this.v.printUsers(users);
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
	
	public void startApp() {
		
		register();
		if(login()) {
			getUsers();
			changeStatus(getUserId(),true);
			sendMsgTo();			
		}
		else {
			
		}
	}
	
	
	public static void main(String[] args) {
		Chat chat = new Chat();
		if(chat.connectApp())
		{
			chat.startApp();
		}
		
	}
	
}
