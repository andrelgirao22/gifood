package br.com.alg.giraofoodapi.core.storage;

import br.com.alg.giraofoodapi.domain.service.FotoStorageService;
import br.com.alg.giraofoodapi.infrastructure.service.storage.DbxFotoStorageService;
import br.com.alg.giraofoodapi.infrastructure.service.storage.LocalFotoStorageService;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    public DbxClientV2 dbxClientV2() {

        String accessToken = storageProperties.getDropbox().getAccessToken();
        String diretorio = storageProperties.getDropbox().getDiretorioFotos();

        DbxRequestConfig config = new DbxRequestConfig("dropbox/" + diretorio);
        DbxClientV2 client = new DbxClientV2(config, accessToken);
        return client;
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if(storageProperties.getTipo().equals(StorageProperties.TipoStorage.DROPBOX)) {
            return new DbxFotoStorageService();
        }
        return new LocalFotoStorageService();
    }

}
