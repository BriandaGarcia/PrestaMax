package mx.uam.tsis2020.prestamax.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis2020.prestamax.datos.ClienteRepository;
import mx.uam.tsis2020.prestamax.negocio.modelo.Cliente;

@Service
@Slf4j
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
public Cliente create(Cliente nuevoCliente) {
		
		// Regla de negocio: no se puede dar de alta un cliente con el mismo id
		Optional <Cliente>  cliente= clienteRepository.findById(nuevoCliente.getIdCliente());
		
		
		if(!cliente.isPresent()) {

			log.info("Voy a guardar al cliente "+nuevoCliente);
			
			Cliente returncliente =  clienteRepository.save(nuevoCliente);
			
			log.info("Voy a regresar al cliente "+returncliente);
			
			return returncliente;
			
		} else {
			
			return null;
			
		}
		
	}


public Iterable <Cliente> retrieveAll() {
	return clienteRepository.findAll();
}

}
