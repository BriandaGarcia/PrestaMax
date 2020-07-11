package mx.uam.tsis2020.prestamax.servicio;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	  notes="Permite crear un nuevo Cleinte, cada cliente debe llevar un identidicador unico, de lo contrario no se puede dar crear"
	)	
	@PostMapping(path = "/clientes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Cliente nuevoCliente) { // Validaciones
				
		//log.info("Recibí llamada a create con "+nuevoAlumno); // Logging
		
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
		
		Iterable <Cliente> result = clienteService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result); 
		
	}
	
	

}
