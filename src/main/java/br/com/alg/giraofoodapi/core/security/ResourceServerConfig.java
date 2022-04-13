package br.com.alg.giraofoodapi.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;

@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
            .and().cors()
            .and()
                .oauth2ResourceServer().jwt();
    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        var secretKey = new SecretKeySpec("89a7assd893564sdasd4as6a5sdasdasda1545ass4s".getBytes(), "HmacSHA256");
//        return NimbusJwtDecoder.withSecretKey(secretKey).build();
//    }

}
