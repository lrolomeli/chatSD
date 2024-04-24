package client;

import javax.swing.SwingUtilities;

public class Chat {
	
	private Client c;
	private View v;
	private AES aes;
	private int cchatid; //current chat ID
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
        
		user = this.v.getUser();
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
	
	public int sendMsgTo() {
		String msg;
		int who;
		who = this.v.whoToTalk();
		if(0 == who) {
			return 0;
		}
		this.cchatid = this.c.getChatId(this.userId,who);
		msg = this.v.askForMsg();
		//System.out.println("receptor "+who);
		//System.out.println("chatid "+this.cchatid);
		//System.out.println("transmisor "+this.userId);
		this.c.sendMessage(this.aes.encrypt(msg), this.userId, this.cchatid);
		return 1;
	}

	public void getGroups() {
		String groups = this.c.getGroups();
		this.v.printList(groups);
	}
	
	public void getUsers() {
		String users = this.c.getUsers();
		this.v.printList(users);
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
					System.out.println("sender: "+m);
					i++;
				}
				else {
					System.out.println(this.aes.decrypt(m));
					i=0;
				}
				
			}
		}
			
	}
	
	public void createGroup() {
		// group admin -> this.userId;
		// group name -> ""
		// chatid group -> create chat id return;
		// register users to chat_membership;
		// chatid & userid
		String name = this.v.askGroupName();
		int[] userlist = this.v.getUserList();
		
		this.cchatid = this.c.createGroup(name, userlist, this.userId);

		System.out.println("chatid: "+this.cchatid);
	}
	
	public void goodbye() {
		this.v.sayGoodBye(this.user);
		changeStatus(this.userId,false);	
	}
	
	private int chat_menu() {
		
		this.v.printChatMenu();
		
		String action = this.v.getAction();

		if(action.equals("1")) {
			String name = this.v.askGroupName();
			int[]userList = this.v.getUserList();
			return this.c.createGroup(name, userList, this.userId);
		}
		else if(action.equals("2")) {
			getUsers();
			sendMsgTo();
			getMessages();
			return 1;
		}
		else if(action.equals("3")){
			getGroups();
			sendMsgTo();
			getMessages();
			return 1;
		
		}
		else {
			
		}
		
		return 0;

	}
	
	public int menu() {
		
		this.v.printMenu();
		
		String action = this.v.getAction();

		if(action.equals("1")) {
			int x=0;
			if(login()) {
				changeStatus(getUserId(),true);
				do {

					x = chat_menu();
					
				} while(0 != x);
				goodbye();
				return 0;
			}
			else {
				return 0;
			}
		}
		else if(action.equals("2")) {
			register();
			
			return 0;
		}
		else {
			return 1;
		}
	}
	
	public void startApp() {
		int x = 0;
		do {
			x = menu();
		}while(0 == x);

	}
	
	
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        		Chat chat = new Chat();
        		if(chat.connectApp())
        		{
        			chat.startApp();
        		}
            }
        });
		
	}
	
}
