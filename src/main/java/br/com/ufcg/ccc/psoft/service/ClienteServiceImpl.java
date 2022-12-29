package br.com.ufcg.ccc.psoft.service;
import java.util.List;
import java.util.stream.Collectors;

import br.com.ufcg.ccc.psoft.dto.requests.ClienteRequestDTO;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufcg.ccc.psoft.exception.ClienteAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.model.Cliente;
import br.com.ufcg.ccc.psoft.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	public ModelMapper modelMapper;

	@Override
	public ClienteRequestDTO getClienteById(Long id) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);
		return modelMapper.map(cliente, ClienteRequestDTO.class);
	}

	private Cliente getClienteId(Long id) throws ClienteNotFoundException {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ClienteNotFoundException());
	}
	@Override
	public void removeClienteCadastrado(Long id) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);
		this.clienteRepository.delete(cliente);
	}

	@Override
	public List<ClienteRequestDTO> listaClientes() {
		List<ClienteRequestDTO> clientesDTO = this.clienteRepository.findAll()
				.stream()
				.map(cliente -> modelMapper.map(cliente, ClienteRequestDTO.class))
				.collect(Collectors.toList());

		return clientesDTO;
	}

	@Override
	public ClienteRequestDTO criaCliente(ClienteRequestDTO clienteRequestDTO) throws ClienteAlreadyCreatedException {
		Cliente cliente = new Cliente(clienteRequestDTO.getCodAcesso(), clienteRequestDTO.getNomeCompleto(),
				clienteRequestDTO.getEnderecoPrincipal());

		this.clienteRepository.save(cliente);

		return modelMapper.map(cliente, ClienteRequestDTO.class);
	}

	@Override
	public ClienteRequestDTO atualizaCliente(Long id, ClienteRequestDTO clienteRequestDTO) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);

		cliente.setEnderecoPrincipal(clienteRequestDTO.getEnderecoPrincipal());
		cliente.setNomeCompleto(clienteRequestDTO.getNomeCompleto());
		this.clienteRepository.save(cliente);

		return modelMapper.map(cliente, ClienteRequestDTO.class);
	}

	@Override
	public Cliente checkCodAcesso(Long id, String codCliente) throws IncorretCodigoAcessoException, ClienteNotFoundException {
		if (!getClienteId(id).getCodAcesso().equals(codCliente)) {
			throw new IncorretCodigoAcessoException();
		}
		return getClienteId(id);
	}

	
}

