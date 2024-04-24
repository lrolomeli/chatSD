package client.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InitFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel title;
	private JTextField usr_field;
	private JPasswordField psw_field;
	private MyButton reg_btn;
	private MyButton login_btn;
	
	public InitFrame() {
		this.setTitle("SDChat");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(200,200);
		this.getContentPane().setBackground(Color.BLACK);
		
		this.title = new JLabel("DIY CHAT!");
		title.setHorizontalTextPosition(JLabel.CENTER);
		title.setVerticalTextPosition(JLabel.CENTER);
		title.setForeground(Color.white);
		title.setFont(new Font("Arial",Font.BOLD|Font.ITALIC, 20));
		
		JLabel usr_label = new JLabel("username: ");
		usr_label.setHorizontalTextPosition(JLabel.CENTER);
		usr_label.setVerticalTextPosition(JLabel.CENTER);
		usr_label.setForeground(Color.white);
		usr_label.setFont(new Font("Arial",Font.BOLD|Font.ITALIC, 16));
		
		this.usr_field = new JTextField();
		
		JLabel psw_label = new JLabel("password: ");
		psw_label.setHorizontalTextPosition(JLabel.CENTER);
		psw_label.setVerticalTextPosition(JLabel.CENTER);
		psw_label.setForeground(Color.white);
		psw_label.setFont(new Font("Arial",Font.BOLD|Font.ITALIC, 16));
		
		this.psw_field = new JPasswordField();

		
		this.reg_btn = new MyButton();
		reg_btn.setText("Registrate");
		reg_btn.setFont(new Font("Arial",Font.BOLD|Font.ITALIC, 14));
		reg_btn.setRadius(20);		
		
		this.login_btn = new MyButton();
		login_btn.setText("Inicia Sesion");
		login_btn.setFont(new Font("Arial",Font.BOLD|Font.ITALIC, 14));
		login_btn.setRadius(20);
		
		reg_btn.addActionListener(this);
		login_btn.addActionListener(this);
		
		JPanel bluePanel = new JPanel();
		bluePanel.setBackground(Color.black);
		bluePanel.setBounds(0,0,200,50);
		
		JPanel greenPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		greenPanel.setBackground(Color.black);
		greenPanel.setBounds(0,50,200,100);
		
		JPanel darkPanel = new JPanel(new GridLayout(2, 1, 10, 10));
		darkPanel.setBackground(Color.black);
		darkPanel.setBounds(0,150,200,50);
		
		bluePanel.add(title);
		
		greenPanel.add(usr_label);
		greenPanel.add(usr_field);
		greenPanel.add(psw_label);
		greenPanel.add(psw_field);
		
		darkPanel.add(reg_btn);
		darkPanel.add(login_btn);
		
		this.add(bluePanel);
		this.add(greenPanel);
		this.add(darkPanel);
		
		this.setLayout(null);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.reg_btn)) {
			System.out.println("hola");
			this.dispose();
			new ConversationGUI();
		}
		else {
			System.out.println("adios");
			this.dispose();
			new ChatFrame();
		}
		
	}
	
}
