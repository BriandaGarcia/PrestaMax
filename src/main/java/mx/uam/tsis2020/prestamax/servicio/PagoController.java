package mx.uam.tsis2020.prestamax.servicio;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis2020.prestamax.negocio.PagoService;
import mx.uam.tsis2020.prestamax.negocio.PrestamoService;
import mx.uam.tsis2020.prestamax.negocio.modelo.Pago;
import mx.uam.tsis2020.prestamax.negocio.modelo.Prestamo;

/**
 * Controlador para el API Rest
 * @author Brianda Garcia
 *
 */
@RestController
@Slf4j
public class PagoController {

	@Autowired
	private PagoService pagoService;
	
	@ApiOperation(
			value = "Crear pago",
			notes = "Permite crear un nuevo pago. Persiste en la BD"
			)
	@PostMapping(path = "/pagos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Pago nuevoPago) {
		
		Pago pago = pagoService.create(nuevoPago);
		log.info("pago tiene "+pago);
		
		if(pago != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(pago); 
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verifique la información"); 
		}
	}
	
	@ApiOperation(
			value = "Recupera todos los pagos",
			notes = "Permite recuperar todos los pagos existentes en la BD"
			) 
	@GetMapping(path = "/pagos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Pago> result = pagoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result); 
	}	

	@ApiOperation(
			value = "Recuperar pago",
			notes = "Permite recuperar un pago mediante su ID, el cual es único"
			)
	@GetMapping(path = "/pagos/{idPago}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieve(@PathVariable("idPago") @Valid Integer idPago) {
		log.info("Buscando el pago con ID "+idPago);
		
		Optional<Pago> pago = pagoService.retrieve(idPago);

		if(pago.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(pago);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago con ID "+idPago+" no encontrado");
		}
	}
	
	@ApiOperation(
			value = "Actualizar pago",
			notes = "Permite actualizar la información de un pago, identificandolo por su ID, el cual no debe ser modificado"
			)
	@PutMapping(path = "/pagos/{idPago}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("idPago") @Valid Integer idPago, @RequestBody @Valid Pago pagoActualizado) {
		
		log.info("Actualizando al pago con ID "+idPago);
			
		//MANDA ACTUALIZAR EL PAGO
		pagoActualizado = pagoService.update(idPago,pagoActualizado);
			
		//SI EL OBJETO DEVUELTO NO ES NULL, EL PAGO SE ACTUALIZÓ CORRECTAMENTE
		if(pagoActualizado != null) {
			return ResponseEntity.status(HttpStatus.OK).body(pagoActualizado);
		} 
		//SI EL OBJETO DEVUELTO ES NULL, EL PAGO QUE SE INTENTA ACTUALIZAR NO EXISTE O LA INFORMACIÓN PROPORCIONADA ES ERRONEA
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago con ID "+idPago+" no encontrado. Verifique la información");
		}
	}
	
	@ApiOperation(
			value = "Borrar pago",
			notes = "Permite borrar un pago, siendo identificado por su ID"
			)
	@DeleteMapping(path = "/pagos/{idPago}")
	public ResponseEntity<?> delete(@PathVariable("idPago") @Valid Integer idPago) {
		
		log.info("Borrando al pago con ID "+idPago);
				
		//SI RECIBE TRUE EL PAGO SE ELIMINÓ CORRECTAMENTE
		if(pagoService.delete(idPago)) {
			return ResponseEntity.status(HttpStatus.OK).body("Pago con ID "+idPago+" eliminado correctamente");
		} 
		//SI RECIBE FALSE, EL PAGO NO EXISTE, POR LO TANTO NO PUEDE SER ELIMINADO
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago con ID "+idPago+" no encontrado. No se ha podido eliminar");
		}
	}
}
