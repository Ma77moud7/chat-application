/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_project;

public class MessageFactory {
    public static Message createMessage(String type, String content, String sender) {
        switch (type.toLowerCase()) {
            case "text":
                return new TextMessage(content, sender);
            case "image":
                return new ImageMessage(content, sender);
            default:
                throw new IllegalArgumentException("Unknown message type: " + type);
        }
    }
}

