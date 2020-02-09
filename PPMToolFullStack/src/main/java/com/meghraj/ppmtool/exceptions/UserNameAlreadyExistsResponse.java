package com.meghraj.ppmtool.exceptions;

public class UserNameAlreadyExistsResponse {
    private String username;

    public UserNameAlreadyExistsResponse() {
    }

    public UserNameAlreadyExistsResponse(String username) {
        this.username = username;
    }

    public String getUsernname() {
        return username;
    }

    public void setUsernname(String usernname) {
        this.username = username;
    }
}
