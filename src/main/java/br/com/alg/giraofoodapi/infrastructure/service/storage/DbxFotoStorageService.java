package br.com.alg.giraofoodapi.infrastructure.service.storage;

import br.com.alg.giraofoodapi.domain.service.FotoStorageService;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

public class DbxFotoStorageService implements FotoStorageService {

    @Autowired
    private DbxClientV2 dbxClient;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            ListFolderResult result = dbxClient.files().listFolderBuilder("")
                    .withIncludeDeleted(false)
                    .withRecursive(true)
                    .withIncludeMediaInfo(true)
                    .start();

            for(Metadata metadata: result.getEntries()) {
                if(metadata.getPathLower().contains(nomeArquivo)) {
                    FileMetadata fileMetadata = (FileMetadata) metadata;
                    InputStream is = dbxClient.files()
                            .download(metadata.getPathLower(), fileMetadata.getRev())
                            .getInputStream();
                    return FotoRecuperada.builder()
                            .inputStream(is)
                            .build();
                }
            }

            return null;
        } catch (DbxException e) {
            throw new StorageException("Não foi possível recuperar a foto", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            dbxClient.files().uploadBuilder("/" + novaFoto.getNomeArquivo())
                    .uploadAndFinish(novaFoto.getInputStream());
        } catch (IOException | DbxException e) {
            throw new StorageException("Não foi possível salvar a foto", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            dbxClient.files().deleteV2("/" + nomeArquivo);
        } catch (DbxException e) {
            throw  new StorageException("Não foi possível excluir arquivo", e);
        }
    }
}
