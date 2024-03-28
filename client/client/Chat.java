package client;

public class Chat {
	
	private Client c;
	private View v;
	private AES aes;
	private int cchatid;
	private String user;
	private int userId;
	
	public Chat() {
		this.c = new Client();
		this.v = new View();
		this.aes = new AES();
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
            if(this.c.registerUser(user, this.aes.encrypt(pass))) {
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
		this.userId = this.c.authUser(this.user, this.aes.encrypt(pass));
        if(0 != this.userId) {
        	System.out.println("Bienvenido "+this.user);
        	return true;
        }
        else {
        	System.out.println("Contraseña incorrecta");
        	return false;
        }
		
	}
	
	public void sendMsgTo() {
		String msg;
		int who;
		who = this.v.whoToTalk();
		this.cchatid = this.c.getChatId(this.userId,who);
		msg = this.v.askForMsg();
		System.out.println("receptor "+who);
		System.out.println("chatid "+this.cchatid);
		System.out.println("transmisor "+this.userId);
		this.c.sendMessage(this.aes.encrypt(msg), this.userId, this.cchatid);
	}
	
	
	public void getUsers() {
		String users = this.c.getUsers();
		this.v.printUsers(users);
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

		for (String r : rows) {
			String[] messages = r.split(":");
			for (String m : messages){
				if(i==0) {
					i++;
				}
				else {
					System.out.println(this.aes.decrypt(m));
					i=0;
				}
				
			}
		}
			
	}
	
	public void startApp() {
		
		register();
		if(login()) {
			getUsers();
			changeStatus(getUserId(),true);
			sendMsgTo();
			getMessages();
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
