package client;

import java.io.Console;
import java.util.Scanner;

public class View {

	private Scanner input;
	
	public View() {
		this.input = new Scanner(System.in);
	}
	
	public int whoToTalk() {

        System.out.print("Who you want to talk to: ");
        int dest = Integer.valueOf(this.input.nextLine());
		return dest;
	}
	
	public String askGroupName() {
        System.out.print("Enter the name of the group: ");
        String msg = this.input.nextLine();
        return msg;
	}
	
	public String askForMsg() {

        System.out.print("Enter a Msg: ");
        String msg = this.input.nextLine();
        if(msg.equals("exit")) {
        	return null;
        }
        return msg;

	}
	
	public String getUser() {
		String user;
		// Pedir usuario por consola,
        System.out.print("User: ");
        user = this.input.nextLine();
        return user;
	}
	
	public String getPassword() {
		String pass;
		// Pedir contraseña por consola,
        Console console = System.console();

        if (console != null) {
            char[] passwordArray = console.readPassword("Enter password: ");
            pass = new String(passwordArray);
        } else {
            System.out.print("Password: ");
            pass = this.input.nextLine();
        }
        
        return pass;
	}
	
	private boolean verifyList(String users) {
		return true;
	}
	
	public int[] getUserList() {
		
        System.out.print("Cuales usuarios (ID's) seran parte del grupo: ");
        String userlist = this.input.nextLine();
        if(verifyList(userlist)) {
    		String[] users = userlist.split(",");
    		
    		int[] usrArr = new int[users.length];
    		
    		for(int i=0;i<users.length;i++) {
    			usrArr[i] = Integer.valueOf(users[i]);
    		}
    		
        	return usrArr;
        }
        else {
        	return null;	
        }
		
	}
	
	public void printUsers(String users) {

        System.out.print(users);

	}
	
}
