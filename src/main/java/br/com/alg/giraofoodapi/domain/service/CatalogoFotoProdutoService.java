package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.model.FotoProduto;
import br.com.alg.giraofoodapi.domain.repository.ProdutoRepository;
import br.com.alg.giraofoodapi.infrastructure.service.storage.LocalFotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream inputStream) {

        Long restauranteId = fotoProduto.getRestauranteId();
        Long produtoId = fotoProduto.getProduto().getId();
        String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());

        Optional<FotoProduto> fotoProdutoExistente =
                produtoRepository.findFotoById(restauranteId, produtoId);

        if(fotoProdutoExistente.isPresent()) {
            produtoRepository.delete(fotoProdutoExistente.get());
        }

        fotoProduto.setNomeArquivo(nomeNovoArquivo);
        FotoProduto foto = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(inputStream)
                .build();

        fotoStorageService.armazenar(novaFoto);

        return foto;
    }

}
