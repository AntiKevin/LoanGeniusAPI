package br.com.loangenius.domain.models;

public enum UserRole {
    ADMIN("admin"),
    uSER("user");

    private String role;


    UserRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
