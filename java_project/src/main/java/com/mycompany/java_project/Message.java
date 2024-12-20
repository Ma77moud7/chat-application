/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_project;

import java.time.LocalDateTime;

public interface Message {
    String getContent();
    String getSender();
    LocalDateTime getTimestamp();
}
