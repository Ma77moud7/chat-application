/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_project;

import java.util.*;

public class PrivateGroup implements Group {
    private String name;
    private List<String> members;
    
    public PrivateGroup(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }
    
    @Override
    public String getName() { return name; }
    
    @Override
    public List<String> getMembers() { return members; }
    
    @Override
    public void addMember(String username) { members.add(username); }
    
    @Override
    public void removeMember(String username) { members.remove(username); }
    
    @Override
    public boolean canSendMessage(String username) { return members.contains(username); }
}