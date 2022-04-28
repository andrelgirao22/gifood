package br.com.alg.giraofoodapi;

import br.com.alg.giraofoodapi.core.io.Base64ProtocolResolver;
import br.com.alg.giraofoodapi.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class GiraofoodApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		var app = new SpringApplication(GiraofoodApiApplication.class);
		app.addListeners(new Base64ProtocolResolver());
		app.run(args);

		//SpringApplication.run(GiraofoodApiApplication.class, args);
	}

}
