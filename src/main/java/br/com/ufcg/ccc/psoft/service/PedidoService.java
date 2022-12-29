package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.PedidoDTO;
import br.com.ufcg.ccc.psoft.exception.*;

public interface PedidoService {

    public PedidoDTO criaPedido(Long idCliente, PedidoDTO pedidoDTO) throws SaborNotFoundException, QuantidadeSaboresInvalidosException, ClienteNotFoundException, IncorretCodigoAcessoException, PagamentoInvalidException;

    public void removerPedidoCadastrado(Long id) throws PedidoNotFoundException;

    PedidoDTO atualizarPedido(Long id, PedidoDTO pedidoDTO) throws PedidoNotFoundException;

    public PedidoDTO getPedidoById(Long idPedido) throws PedidoNotFoundException;

    PedidoDTO confirmarPedido(Long id, PedidoDTO pedidoDTO) throws PedidoNotFoundException;

    PedidoDTO finalizarPedido(Long id, PedidoDTO pedidoDTO) throws PedidoNotFoundException;

}
