package br.com.loangenius.entities.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
