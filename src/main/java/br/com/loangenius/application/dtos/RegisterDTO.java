package br.com.loangenius.application.dtos;

import br.com.loangenius.domain.models.UserRole;

public record RegisterDTO(String username, String password, UserRole role) {
}
