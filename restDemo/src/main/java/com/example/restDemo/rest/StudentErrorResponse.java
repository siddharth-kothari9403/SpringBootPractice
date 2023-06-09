package com.example.restDemo.rest;

//custom pojo class for errors
public class StudentErrorResponse {
    private String message;
    private int status;
    private long timestamp;

    public StudentErrorResponse(){

    }

    public StudentErrorResponse(int status, String message, long timestamp){
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
