package client;

import javax.swing.SwingUtilities;

public class Main {
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
