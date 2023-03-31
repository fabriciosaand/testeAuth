package com.fabriciosaand.testeauth.dto;

public class ErroDTO {

    private int status;
    private String message;

    public ErroDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErroDTO() {
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
}
