package mx.uam.tsis2020.prestamax.servicio;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis2020.prestamax.negocio.PrestamoService;
import mx.uam.tsis2020.prestamax.negocio.modelo.Empleado;
import mx.uam.tsis2020.prestamax.negocio.modelo.Prestamo;


@RestController
@Slf4j
public class PrestamoController {
	
	@Autowired
	private PrestamoService prestamoService;
	
	
	@PostMapping(path = "/prestamos/clientes/empleados", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Prestamo nuevoPrestamo,
			@RequestParam("idCliente") Integer idCliente,
			@RequestParam("idEmpleado") Integer idEmpleado) {
		
		Prestamo prestamo = prestamoService.create(nuevoPrestamo,idCliente,idEmpleado);
		log.info("prestamo tiene "+prestamo);
		if(prestamo!=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(prestamo); 
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
		}
		
	
	}
	

	@ApiOperation(
			value = "Recupera todos los prestamos",
			notes = "Permite recuperar todos los prestamos existentes"
			) 
	@GetMapping(path = "/prestamos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Prestamo> result = prestamoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result); 
	}	

}
