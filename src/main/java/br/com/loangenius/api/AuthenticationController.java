package br.com.loangenius.api;

import br.com.loangenius.application.dtos.AuthenticatedUserResponse;
import br.com.loangenius.application.dtos.AuthenticationDTO;
import br.com.loangenius.application.dtos.LoginResponseDTO;
import br.com.loangenius.application.dtos.RegisterDTO;
import br.com.loangenius.domain.models.User;
import br.com.loangenius.domain.exceptions.BadRequestException;
import br.com.loangenius.domain.repositories.UserRepository;
import br.com.loangenius.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity token(@RequestBody @Valid AuthenticationDTO data) throws BadRequestException {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        }catch (Exception exception){
        throw new BadRequestException("erro na requisição");
        }


    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if (this.userRepository.findByLogin(data.username()) != null) {
            throw new BadRequestException("Nome de usuário indisponível");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.username(), encryptedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/user")
    public ResponseEntity<AuthenticatedUserResponse> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();

        AuthenticatedUserResponse response = new AuthenticatedUserResponse();
        response.setLogin(principal.getUsername());
        response.setRoles(principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}
