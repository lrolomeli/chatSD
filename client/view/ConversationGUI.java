package client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConversationGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea conversationTextArea;
    private JTextField messageTextField;
    private JButton sendButton;

    public ConversationGUI() {
        setTitle("Conversation GUI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        conversationTextArea = new JTextArea();
        conversationTextArea.setEditable(false); // Make it read-only
        JScrollPane scrollPane = new JScrollPane(conversationTextArea);
        messageTextField = new JTextField(30);
        sendButton = new JButton("Send");

        // Set layout
        setLayout(new BorderLayout());

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(messageTextField, BorderLayout.SOUTH);
        add(sendButton, BorderLayout.EAST);

        // Add action listener to send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        
		this.setVisible(true);
    }

    private void sendMessage() {
        String message = messageTextField.getText();
        if (!message.isEmpty()) {
            conversationTextArea.append("You: " + message + "\n");
            messageTextField.setText(""); // Clear the text field after sending the message
        }
    }

}
