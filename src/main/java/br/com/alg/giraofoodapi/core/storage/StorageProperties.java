package br.com.alg.giraofoodapi.core.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("gifood.storage")
public class StorageProperties {

    private Local local = new Local();

    private Dropbox dropbox = new Dropbox();

    @Getter
    @Setter
    public class Local {
        private Path diretorioFotos;
    }

    @Getter
    @Setter
    public class Dropbox {
        private String accessToken;
        private String diretorioFotos;
    }
}
