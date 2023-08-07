package br.com.loangenius.entities.user;

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
