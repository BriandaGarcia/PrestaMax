package mx.uam.tsis2020.prestamax.negocio;

import java.util.Optional;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis2020.prestamax.PrestamaxApplication;
import mx.uam.tsis2020.prestamax.datos.ClienteRepository;
import mx.uam.tsis2020.prestamax.negocio.modelo.Cliente;

@Service
@Slf4j
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente create(Cliente nuevoCliente) {
	
		log.info("Voy a guardar al cliente "+nuevoCliente);
			
		nuevoCliente =  clienteRepository.save(nuevoCliente);
			
		log.info("Voy a regresar al cliente "+nuevoCliente);
			
		return nuevoCliente;
	}

   //recupera todos los clientes
   public Iterable <Cliente> retrieveAll()
   {
	return clienteRepository.findAll();
   }
   
   //recupera cliente por id
   public Cliente retrieve(Integer idCliente)
	{
	  
		Optional<Cliente>  clientefind =clienteRepository.findById(idCliente);
		
		if(clientefind.isPresent())
		{
			 return clientefind.get();
		}else
			return null;
	}
   
   public Cliente updateCliente(Integer idCliente, Cliente actualizaCliente)
	{
		 Optional<Cliente>  clientefind =clienteRepository.findById(idCliente);
		if(clientefind.isPresent())
		{
			Cliente cliente = new Cliente();
		
		    cliente.setApellidoPaterno(actualizaCliente.getApellidoPaterno());
		    cliente.setApellidoMaterno(actualizaCliente.getApellidoMaterno());
		    cliente.setNombre(actualizaCliente.getNombre());
		    cliente.setIne(actualizaCliente.getIne());
		    cliente.setDireccion(actualizaCliente.getDireccion());
		    cliente.setTelefono(actualizaCliente.getTelefono());
		    cliente.setSalario(actualizaCliente.getSalario());
		    cliente.setStatus(actualizaCliente.getStatus());
		    
		    Cliente actualizadoCliente = clienteRepository.save(actualizaCliente);
		
		return actualizadoCliente;
		}
		else
		{
			return null;
		}
	}

   public Double retrieveSalary(Integer idCliente)
	{
	  
		Optional<Cliente>  clientefind =clienteRepository.findById(idCliente);

		
		if(clientefind.isPresent())
		{
			log.info("regreso el salario "+ clientefind.get().getSalario());
			 return clientefind.get().getSalario();
			  
		}else
			return null;
	}
   
	public boolean delete(Integer idCliente) {
		//Recupero el prestamo que se intenta eliminar
		Optional<Cliente> prestamo = clienteRepository.findById(idCliente);
		
		//Confirmo que existe
		if(prestamo.isPresent()) {
			clienteRepository.deleteById(idCliente);
			return true;
		} else {
			return false;
		}
	}

}
