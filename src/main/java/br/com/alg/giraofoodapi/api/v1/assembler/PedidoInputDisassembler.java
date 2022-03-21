package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.model.input.PedidoInput;
import br.com.alg.giraofoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoInput input) {
        return modelMapper.map(input, Pedido.class);
    }

    public void copyToDomainInObject(PedidoInput input, Pedido pedido) {
        modelMapper.map(input, pedido);
    }

}
