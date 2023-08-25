package br.com.loangenius;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "LoanGenius API",
		version = "0.1",
		description = "API to manage and predict personal investments and loan bills"))
public class LoanGeniusApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanGeniusApplication.class, args);
	}

}
