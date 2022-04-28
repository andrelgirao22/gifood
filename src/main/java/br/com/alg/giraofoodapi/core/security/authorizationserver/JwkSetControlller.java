package br.com.alg.giraofoodapi.core.security.authorizationserver;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JwkSetControlller {

    @Autowired
    private JWKSet jwkSet;

    @GetMapping("/.well-know/jwks.json")
    public Map<String, Object> keys() {
        System.out.println("JWKS Token");
        return this.jwkSet.toJSONObject();
    }
}
