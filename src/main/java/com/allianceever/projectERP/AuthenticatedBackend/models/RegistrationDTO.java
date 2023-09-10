package com.allianceever.projectERP.AuthenticatedBackend.models;

public class RegistrationDTO {
    private String username;
    private String password;
    private String role;

    public RegistrationDTO(){
        super();
    }

    public RegistrationDTO(String username, String password, String role){
        super();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getRole(){
        return this.role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String toString(){
        return "Registration info: username: " + this.username + " password: " + this.password + " role: " + this.role;
    }
}
