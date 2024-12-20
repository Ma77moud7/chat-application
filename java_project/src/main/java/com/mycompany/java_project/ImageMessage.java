/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_project;

import java.time.LocalDateTime;

public class ImageMessage implements Message {
    private String imagePath;
    private String sender;
    private LocalDateTime timestamp;
    
    public ImageMessage(String imagePath, String sender) {
        this.imagePath = imagePath;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
    }
    
    @Override
    public String getContent() { return imagePath; }
    
    @Override
    public String getSender() { return sender; }
    
    @Override
    public LocalDateTime getTimestamp() { return timestamp; }
}

