package com.mycompany.java_project;

public class GroupFactory {
    public static Group createGroup(String type, String name) {
        switch (type.toLowerCase()) {
            case "public":
                return new PublicGroup(name);
            case "private":
                return new PrivateGroup(name);
            default:
                throw new IllegalArgumentException("Unknown group type: " + type);
        }
    }
}
