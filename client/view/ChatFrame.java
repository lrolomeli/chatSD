package client.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel title;
	
	public ChatFrame() {
		this.setTitle("SDChat");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(200,200);
		this.getContentPane().setBackground(Color.BLACK);
		
		this.title = new JLabel("NUEVA VENTANA!");
		title.setHorizontalTextPosition(JLabel.CENTER);
		title.setVerticalTextPosition(JLabel.CENTER);
		title.setForeground(Color.white);
		title.setFont(new Font("Arial",Font.BOLD|Font.ITALIC, 20));
		
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
		
		this.add(bluePanel);
		this.add(greenPanel);
		this.add(darkPanel);
		
		this.setLayout(null);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		
	}
	
}