package com.khoding.auth.response;

import org.springframework.stereotype.Component;

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public static MessageResponse buildMessage(String message){
        return new MessageResponse(message);
    }

    public String getMessage() {
        return message;
    }

//    public void setMessage(String message) {
//        this.message = message;
//    }
}
