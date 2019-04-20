package com.study.android.ex55_firebasechat;

public class ChatDTO {
    private String userName;
    private String message;
    private String rating;

    public ChatDTO() {}

    public ChatDTO(String rating) {
        this.rating = rating;
    }

    public ChatDTO(String userName, String message, String rating) {
        this.userName = userName;
        this.message = message;
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
