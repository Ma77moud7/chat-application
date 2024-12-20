package com.mycompany.java_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ChatApplication extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JList<String> chatsList;
    private JList<String> groupsList;  // New list for groups
    private String currentUser;
    private String currentChat;
    private JButton sendButton;
    private JButton messageTypeButton;  // New button for message type
    private DefaultListModel<String> chatsListModel;
    private DefaultListModel<String> groupsListModel;  // Model for groups

    public ChatApplication() {
        setTitle("Chat Application");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Set the layout of the main window
        setLayout(new BorderLayout());

        // Left panel (Chats + Groups)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        
        // Upper panel for Chats
        JPanel upperLeftPanel = new JPanel(new BorderLayout());
        chatsListModel = new DefaultListModel<>();
        chatsList = new JList<>(chatsListModel);
        chatsList.setPreferredSize(new Dimension(200, 0));
        chatsList.setBorder(BorderFactory.createTitledBorder("Chats"));
        JScrollPane chatsScrollPane = new JScrollPane(chatsList);
        upperLeftPanel.add(chatsScrollPane, BorderLayout.CENTER);
        
        // Lower panel for Groups
        JPanel lowerLeftPanel = new JPanel(new BorderLayout());
        groupsListModel = new DefaultListModel<>();
        groupsList = new JList<>(groupsListModel);
        groupsList.setPreferredSize(new Dimension(200, 0));
        groupsList.setBorder(BorderFactory.createTitledBorder("Groups"));
        JScrollPane groupsScrollPane = new JScrollPane(groupsList);
        lowerLeftPanel.add(groupsScrollPane, BorderLayout.CENTER);
        
        // Add both the upper and lower panels to the left panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperLeftPanel, lowerLeftPanel);
        splitPane.setDividerLocation(250); // Adjust divider position if needed
        leftPanel.add(splitPane, BorderLayout.CENTER);

        // Chat area panel
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        // Message input panel (with buttons)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        messageField = new JTextField(30); // Size of the text input field
        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // New button for Message Type
        messageTypeButton = new JButton("Message Type");
        messageTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMessageTypeDialog();  // Show the message type selection dialog
            }
        });

        bottomPanel.add(messageField);
        bottomPanel.add(sendButton);
        bottomPanel.add(messageTypeButton);

        // Assemble layout
        add(chatScrollPane, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);

        // Initial state (disable buttons until login)
        messageField.setEnabled(false);
        sendButton.setEnabled(false);
        messageTypeButton.setEnabled(false);

        // Add a listener for chat selection to display static text in the chat area
        chatsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedChat = chatsList.getSelectedValue();
                if (selectedChat != null) {
                    showChatContent(selectedChat);
                    currentChat = selectedChat;
                }
                
                    
            }
        });

        // Add a listener for group selection to display static content for the group
    groupsList.addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        String selectedGroup = groupsList.getSelectedValue();
        if (selectedGroup != null) {
            showGroupContent(selectedGroup);  // Display content for the selected group
            currentChat = selectedGroup;      // Set current chat to the group name
            chatArea.revalidate();            // Refresh the chat area
            chatArea.repaint();               // Repaint the chat area
        }
    }
});
    }

    private void showLoginDialog() {
        JDialog loginDialog = new JDialog(this, "Login", true);
        loginDialog.setLayout(new GridLayout(3, 2, 10, 10));
        loginDialog.setSize(300, 150);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginDialog.add(new JLabel("  Username:"));
        loginDialog.add(usernameField);
        loginDialog.add(new JLabel("  Password:"));
        loginDialog.add(passwordField);
        loginDialog.add(new JLabel("")); // Empty space
        loginDialog.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentUser = usernameField.getText();
                loginDialog.dispose();
                updateUI();  // Update the UI and show the main interface
            }
        });

        loginDialog.setLocationRelativeTo(this);
        loginDialog.setVisible(true);
    }

    private void updateUI() {
        // Enable message-related components after login
        messageField.setEnabled(true);
        sendButton.setEnabled(true);
        messageTypeButton.setEnabled(true);

        // Update the chats list with sample data
        chatsListModel.addElement("Chat with John Doe");
        chatsListModel.addElement("Chat with Alice");
        groupsListModel.addElement("Group Chat: Public");
        groupsListModel.addElement("Group Chat: Private");
        groupsListModel.addElement("Group Chat: Admin");

        // Make the main interface visible
        setVisible(true);
    }

    private void sendMessage() {
        String content = messageField.getText().trim();
        if (!content.isEmpty() && currentChat != null) {
            // Create a new message and add it to the chat
            Message message = new Message(content, currentUser) {};
            ChatSessionManager.getInstance().addMessage(currentChat, message);
            updateChat();
            messageField.setText("");
        }
    }

    private void updateChat() {
        chatArea.setText("");
        List<Message> messages = ChatSessionManager.getInstance().getChatHistory(currentChat);
        for (Message message : messages) {
            chatArea.append(String.format("[%s] %s: %s\n",
                    message.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                    message.getSender(),
                    message.getContent()));
        }
    }

    private void showChatContent(String chatName) {
        // Static chat content based on the selected chat
        if (chatName.equals("Chat with John Doe")) {
            chatArea.setText("This is a static conversation with John Doe.\n"
                    + "John: Hey, how's it going?\n"
                    + "You: I'm good, how about you?\n"
                    + "John: All good, just catching up with some work.\n"
                    + "You: Same here, just taking a break.\n");
        } else if (chatName.equals("Chat with Alice")) {
            chatArea.setText("Welcome to the Friends group chat!\n"
                    + "Alice: Hey everyone, let's meet up this weekend.\n"
                    + "Bob: That sounds great! I'm in.\n"
                    + "Charlie: Same here!\n"
                    + "You: I'm in too, let me know the details.\n");
        }
        // Add more static chats as needed
    }

    private void showGroupContent(String groupName) {
    System.out.println("Showing content for group: " + groupName);  // Debugging
    // Group content handling
    if (groupName.equals("Public")) {
        chatArea.setText("Welcome to the Public group chat!\n");
    } else if (groupName.equals("Private")) {
        chatArea.setText("Welcome to the Private group chat!\n");
    } else if (groupName.equals("Admin")) {
        chatArea.setText("Welcome to the Admin group chat!\n");
    }

    // You can add more group chats as needed
}

    private void showMessageTypeDialog() {
        // Create a dialog to choose between Text, Image, and Video
        JDialog messageTypeDialog = new JDialog(this, "Select Message Type", true);
        messageTypeDialog.setLayout(new GridLayout(4, 1, 10, 10));
        messageTypeDialog.setSize(300, 200);

        JButton textButton = new JButton("Text");
        JButton imageButton = new JButton("Image");
        JButton videoButton = new JButton("Video");

        textButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                messageTypeDialog.dispose();
                messageField.setEnabled(true);
                sendButton.setEnabled(true);  // Enable send button
            }
        });

        imageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                messageTypeDialog.dispose();
                openFileChooser("Select an image", "jpg", "png", "gif");
            }
        });

        videoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                messageTypeDialog.dispose();
                openFileChooser("Select a video", "mp4", "avi", "mov");
            }
        });

        messageTypeDialog.add(textButton);
        messageTypeDialog.add(imageButton);
        messageTypeDialog.add(videoButton);

        messageTypeDialog.setLocationRelativeTo(this);
        messageTypeDialog.setVisible(true);
    }

    private void openFileChooser(String description, String... extensions) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Selected file: " + selectedFile.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ChatApplication app = new ChatApplication();
                app.showLoginDialog();  // Show login dialog at the start
            }
        });
    }
}

class ChatSessionManager {
    private static final ChatSessionManager instance = new ChatSessionManager();
    private final Map<String, List<Message>> chats;

    private ChatSessionManager() {
        chats = new HashMap<>();
    }

    public static ChatSessionManager getInstance() {
        return instance;
    }

    public List<Message> getChatHistory(String chatName) {
        return chats.getOrDefault(chatName, new ArrayList<>());
    }

    public void addMessage(String chatName, Message message) {
        chats.computeIfAbsent(chatName, k -> new ArrayList<>()).add(message);
    }
}

