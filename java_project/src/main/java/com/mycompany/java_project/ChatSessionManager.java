package com.mycompany.java_project;

import java.util.*; 

public class ChatSessionManager {
    private static ChatSessionManager instance;
    private Map<String, List<Message>> chatHistory; // Stores messages for each chat (Key: Chat ID, Value: List of Messages)
    private Set<String> activeUsers; // Stores active users (Unique user names)

    // Private constructor to prevent instantiation from outside
    private ChatSessionManager() {
        chatHistory = new HashMap<>(); // Initialize chat history
        activeUsers = new HashSet<>(); // Initialize active users set
    }

    // Get the single instance of the ChatSessionManager (Singleton Pattern)
    public static synchronized ChatSessionManager getInstance() {
        if (instance == null) {
            instance = new ChatSessionManager();
        }
        return instance;
    }

    // Add a message to a specific chat
    public void addMessage(String chatId, Message message) {
        // If the chat doesn't exist yet, create a new list of messages for it
        chatHistory.computeIfAbsent(chatId, k -> new ArrayList<>()).add(message);
    }

    // Get all messages from a chat (returns an empty list if chat not found)
    public List<Message> getChatHistory(String chatId) {
        return chatHistory.getOrDefault(chatId, new ArrayList<>());
    }

    // Add a new active user
    public void addActiveUser(String username) {
        activeUsers.add(username);
    }

    // Remove an active user
    public void removeActiveUser(String username) {
        activeUsers.remove(username);
    }

    // Get all active users
    public Set<String> getActiveUsers() {
        return new HashSet<>(activeUsers); // Return a new set to avoid direct modification
    }
}
