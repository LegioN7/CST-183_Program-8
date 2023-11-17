package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GamePrefs {
    private String userName;
    private boolean receiveEmails;
    private boolean receiveTextMessages;
    private String difficulty;
    private double gameSpeed;
    private String colorSelection;
    private final LocalDateTime timestamp;

    // Constructor
    public GamePrefs(String userName, boolean receiveEmails, boolean receiveTextMessages,
                     String difficulty, double gameSpeed, String colorSelection) {
        this.userName = userName;
        this.receiveEmails = receiveEmails;
        this.receiveTextMessages = receiveTextMessages;
        this.difficulty = difficulty;
        this.gameSpeed = gameSpeed;
        this.colorSelection = colorSelection;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isReceiveEmails() {
        return receiveEmails;
    }

    public void setReceiveEmails(boolean receiveEmails) {
        this.receiveEmails = receiveEmails;
    }

    public boolean isReceiveTextMessages() {
        return receiveTextMessages;
    }

    public void setReceiveTextMessages(boolean receiveTextMessages) {
        this.receiveTextMessages = receiveTextMessages;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public double getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(double gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public String getColorSelection() {
        return colorSelection;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // toString method to display a summary of the data
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = timestamp.format(formatter);

        return "User Name: " + userName +
                "\n\nCommunication Methods" +
                "\nText: " + (receiveTextMessages ? "Yes" : "No") +
                "\nEmail: " + (receiveEmails ? "Yes" : "No") +
                "\n\nDifficulty: " + difficulty +
                "\n\nGame Speed: " + gameSpeed + "%" +
                "\n\nColor Selection: " + colorSelection +
                "\n\nSubmit Time: " + formattedTimestamp;
    }
}