package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.exception.FotoProdutoNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.model.FotoProduto;
import br.com.alg.giraofoodapi.domain.repository.ProdutoRepository;
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
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoProdutoExistente =
                produtoRepository.findFotoById(restauranteId, produtoId);

        if(fotoProdutoExistente.isPresent()) {
            nomeArquivoExistente = fotoProdutoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoProdutoExistente.get());
        }

        fotoProduto.setNomeArquivo(nomeNovoArquivo);
        FotoProduto foto = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(inputStream)
                .build();

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    public FotoProduto buscar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                        .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscar(restauranteId, produtoId);
        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();

        fotoStorageService.remover(fotoProduto.getNomeArquivo());
    }
}
