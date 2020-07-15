package mx.uam.tsis2020.prestamax.servicio;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import mx.uam.tsis2020.prestamax.negocio.ClienteService;
import mx.uam.tsis2020.prestamax.negocio.modelo.Cliente;

@RestController
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@ApiOperation
	(//documentacion del api
	  value = "Crear un nuevo Cliente",
	  notes="Permite crear un nuevo Cliente, cada cliente debe llevar un identidicador unico, de lo contrario no se puede dar crear"
	)	
	@PostMapping(path = "/clientes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Cliente nuevoCliente) { // Validaciones
				
		
		Cliente cliente = clienteService.create(nuevoCliente);
		
		if(cliente != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(cliente);			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se puede crear cliente");
		}
	

	}
	
	
	@ApiOperation
	(//documentacion del api
	  value = "Recupera todos los Clientes",
	  notes="Recupera todos los clientes que se crearon"
    )
    @GetMapping(path = "/cleintes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Cliente> clientes = clienteService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(clientes); 
		
	}
	
	@ApiOperation
	(//documentacion del api
	  value = "Recupera un Cliente por id",
	  notes="Recupera un cliente por medio de un id existente para ver su información"
	)
	@GetMapping(path = "/clientes/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("idCliente") Integer idCliente) {
		
		
		Cliente cliente = clienteService.retrieve(idCliente);
		
		if(cliente != null) {
			return ResponseEntity.status(HttpStatus.OK).body(cliente);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente con id: "+idCliente +" no existe");
		}
		
		
	}
	
	
	@ApiOperation
	(//documentacion del api
	  value = "Actualiza Cliente",
	  notes="Actualiza los datos del Cliente por medio de su id (idCliente)"
	)
	@PutMapping(path = "clientes/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@PathVariable("idCliente") Integer idCliente,@RequestBody Cliente actualizaCliente)
	{
 		Cliente cliente= clienteService.updateCliente(idCliente, actualizaCliente);
 		if(cliente!=null)
 		{
 	      return ResponseEntity.status(HttpStatus.OK).body(cliente);
 		}
 		else
 		{
 			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
 		}
	}
	
	
	
	

}
