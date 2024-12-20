package com.mycompany.java_project;

import java.util.List;

public interface Group {
    String getName();
    List<String> getMembers();
    void addMember(String username);
    void removeMember(String username);
    boolean canSendMessage(String username);
}
