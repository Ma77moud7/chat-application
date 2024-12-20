package com.mycompany.java_project;

import java.util.*;

public class AuthenticationManager {
    private static AuthenticationManager instance;
    private Map<String, String> users;
    private Set<String> loggedInUsers;
    
    private AuthenticationManager() {
        users = new HashMap<>();
        loggedInUsers = new HashSet<>();
        // Add demo users
        users.put("admin", "admin123");
        users.put("user1", "pass123");
        users.put("user2", "pass123");
        users.put("user3", "pass123");
    }
    
    public static synchronized AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }
    
    public boolean login(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            loggedInUsers.add(username);
            ChatSessionManager.getInstance().addActiveUser(username);
            return true;
        }
        return false;
    }
    
    public void logout(String username) {
        loggedInUsers.remove(username);
        ChatSessionManager.getInstance().removeActiveUser(username);
    }
    
    public Set<String> getActiveUsers() {
        return new HashSet<>(loggedInUsers);
    }
    
    public boolean isUserLoggedIn(String username) {
        return loggedInUsers.contains(username);
    }
}
