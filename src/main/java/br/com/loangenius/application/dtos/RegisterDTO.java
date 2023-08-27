package br.com.loangenius.application.dtos;

import br.com.loangenius.domain.models.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
