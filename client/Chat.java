package client;

public class Chat {
	
	private Client c;
	private View v;
	private String user;
	
	public Chat() {
		this.c = new Client();
		this.v = new View();
	}
	
	public String register() {
		String user, pass;
		
        user = this.v.getUser();
        pass = this.v.getPassword();
		
        if(this.c.registerUser(user, pass)) {
        	return user;
        }
        else
        {
            return null;
        }
		
	}
	
	public String login() {
		String user, pass;
		
        user = this.v.getUser();
        pass = this.v.getPassword();
		
        if(this.c.authUser(user, pass)) {
        	return user;
        }
        else
        {
            return null;
        }
		
	}
	
	public void sendMsgTo() {
		String msg, who;
		who = this.v.whoToTalk();
		msg = this.v.askForMsg();
		this.c.sendMessage(msg, this.user, who);
	}
	
	public static void main(String[] args) {
		Chat chat = new Chat();
		
		chat.register();
		chat.login();
		chat.sendMsgTo();
		
	}
	
}
