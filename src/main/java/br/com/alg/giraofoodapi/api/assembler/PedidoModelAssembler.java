package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.api.model.dto.PedidoDTO;
import br.com.alg.giraofoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO toDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> toCollection(Collection<Pedido> pedidos) {
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
