package com.taskManager.taskManager.dto;

public class JwtResponse {

	private String token;

    public JwtResponse() {}

    public JwtResponse(String token) {
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
	
}
