/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_project;

import java.time.LocalDateTime;

public class TextMessage implements Message {
    private String content;
    private String sender;
    private LocalDateTime timestamp;
    
    public TextMessage(String content, String sender) {
        this.content = content;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
    }
    
    @Override
    public String getContent() { return content; }
    
    @Override
    public String getSender() { return sender; }
    
    @Override
    public LocalDateTime getTimestamp() { return timestamp; }
}
