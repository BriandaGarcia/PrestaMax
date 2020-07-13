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
import mx.uam.tsis2020.prestamax.negocio.ClienteService;
import mx.uam.tsis2020.prestamax.negocio.PrestamoService;
import mx.uam.tsis2020.prestamax.negocio.modelo.Cliente;
import mx.uam.tsis2020.prestamax.negocio.modelo.Prestamo;

/**
 * Controlador para el API Rest
 * @author Brianda Garcia
 *
 */
@RestController
@Slf4j
public class PrestamoController {
	
	@Autowired
	private PrestamoService prestamoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@ApiOperation(
			value = "Crear prestamo",
			notes = "Permite crear un nuevo prestamo. Persiste en la BD"
			)
	@PostMapping(path = "/prestamos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Prestamo nuevoPrestamo) {
		
		Prestamo prestamo = prestamoService.create(nuevoPrestamo);
		log.info("nuevo prestamo  "+nuevoPrestamo);
		
		if(prestamo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(prestamo); 
		} else {
			if(prestamoService.validaExisteCliente(nuevoPrestamo.getIdCliente())) {
				if(prestamoService.validaExisteEmpleado(nuevoPrestamo.getIdEmpleado())) {
					Cliente cliente= clienteService.retrieve(nuevoPrestamo.getIdCliente());	
					if(prestamoService.validaStatusCliente(cliente.getStatus())) {
						if(prestamoService.validaCantidad(cliente.getSalario(), nuevoPrestamo.getCantidad())) {
							if(prestamoService.validaTasaInteres(nuevoPrestamo.getTasaInteres())) {
								if(prestamoService.validaNumeroPagos(nuevoPrestamo.getNumeroPagos())) {
									if(prestamoService.validaDiaPago(nuevoPrestamo.getDiaPago())) {
										if(prestamoService.validaCantidadPago(nuevoPrestamo)) {
											if(prestamoService.validaPenalizacion(nuevoPrestamo.getCantidad(), nuevoPrestamo.getPenalizacionDia())) {
												return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
											} else {
												return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Penalizacion asignada incorrecta. Verifique la informacion");
											}
										} else {
											return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cantidad de cada pago incorrecta. Verifique la informacion");
										}
									} else {
										return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Dia de pago debe ser el 1. Verifique la informacion");
									}
								} else {
									return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Numero de pagos incorrecto. Verifique la informacion");
								}
							} else {
								return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Tasa de interes fuera de los limites. Verifique la informacion");
							}
						} else {
							return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cantidad no apta para prestamo. Verifique la informacion");
						}
					} else {
						return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cliente no apto para otro prestamo. Verifique la informacion");
					}
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no existente. Verifique la informacion");
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no existente. Verifique la informacion");
			} 
		}
	}
	
	@ApiOperation(
			value = "Recupera todos los prestamos",
			notes = "Permite recuperar todos los prestamos existentes en la BD"
			) 
	@GetMapping(path = "/prestamos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Prestamo> result = prestamoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result); 
	}	

	@ApiOperation(
			value = "Recuperar prestamo",
			notes = "Permite recuperar un prestamo mediante su ID, el cual es único"
			)
	@GetMapping(path = "/prestamos/{idPrestamo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieve(@PathVariable("idPrestamo") @Valid Integer idPrestamo) {
		log.info("Buscando el prestamo con ID "+idPrestamo);
		
		Optional<Prestamo> prestamo = prestamoService.retrieve(idPrestamo);

		if(prestamo.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(prestamo);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Préstamo con ID "+idPrestamo+" no encontrado");
		}
	}
	
	@ApiOperation(
			value = "Actualizar préstamo",
			notes = "Permite actualizar la información de un prestamo, identificandolo por su ID, el cual no debe ser modificado"
			)
	@PutMapping(path = "/prestamos/{idPrestamo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("idPrestamo") @Valid Integer idPrestamo, @RequestBody @Valid Prestamo prestamoActualizado) {
		
		log.info("Actualizando al prestamo con ID "+idPrestamo);
			
		//MANDA ACTUALIZAR EL PRESTAMO
		Prestamo prestamo = prestamoService.update(idPrestamo,prestamoActualizado);
			
		//SI EL OBJETO DEVUELTO NO ES NULL, EL PRESTAMO SE ACTUALIZÓ CORRECTAMENTE
		if(prestamo != null) {
			return ResponseEntity.status(HttpStatus.OK).body(prestamoActualizado);
		} 
		//SI EL OBJETO DEVUELTO ES NULL, EL PRESTAMO QUE SE INTENTA ACTUALIZAR NO EXISTE O LA INFORMACIÓN PROPORCIONADA ES ERRONEA
		else {
			if(prestamoService.validaExistePrestamo(idPrestamo)) {
				if(prestamoService.validaExisteCliente(prestamoActualizado.getIdCliente())) {
					if(prestamoService.validaExisteEmpleado(prestamoActualizado.getIdEmpleado())) {
						Cliente cliente= clienteService.retrieve(prestamoActualizado.getIdCliente());	
						if(prestamoService.validaStatusCliente(cliente.getStatus())) {
							if(prestamoService.validaCantidad(cliente.getSalario(), prestamoActualizado.getCantidad())) {
								if(prestamoService.validaTasaInteres(prestamoActualizado.getTasaInteres())) {
									if(prestamoService.validaNumeroPagos(prestamoActualizado.getNumeroPagos())) {
										if(prestamoService.validaDiaPago(prestamoActualizado.getDiaPago())) {
											if(prestamoService.validaCantidadPago(prestamoActualizado)) {
												if(prestamoService.validaPenalizacion(prestamoActualizado.getCantidad(), prestamoActualizado.getPenalizacionDia())) {
													return ResponseEntity.status(HttpStatus.CREATED).body(prestamoActualizado);
												} else {
													return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Penalizacion asignada incorrecta. Verifique la informacion");
												}
											} else {
												return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cantidad de cada pago incorrecta. Verifique la informacion");
											}
										} else {
											return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Dia de pago debe ser el 1. Verifique la informacion");
										}
									} else {
										return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Numero de pagos incorrecto. Verifique la informacion");
									}
								} else {
									return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Tasa de interes fuera de los limites. Verifique la informacion");
								}
							} else {
								return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cantidad no apta para prestamo. Verifique la informacion");
							}
						} else {
							return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cliente no apto para otro prestamo. Verifique la informacion");
						}
					} else {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no existente. Verifique la informacion");
					}
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no existente. Verifique la informacion");
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prestamo no existente. Verifique la informacion");
			}
		}
	}
	
	@ApiOperation(
			value = "Borrar préstamo",
			notes = "Permite borrar un préstamo, siendo identificado por su ID"
			)
	@DeleteMapping(path = "/prestamos/{idPrestamo}")
	public ResponseEntity<?> delete(@PathVariable("idPrestamo") @Valid Integer idPrestamo) {
		
		log.info("Borrando al prestamo con ID "+idPrestamo);
				
		//SI RECIBE TRUE EL PRESTAMO SE ELIMINÓ CORRECTAMENTE
		if(prestamoService.delete(idPrestamo)) {
			return ResponseEntity.status(HttpStatus.OK).body("Prestamo con ID "+idPrestamo+" eliminado correctamente");
		} 
		//SI RECIBE FALSE, EL PRESTAMO NO EXISTE, POR LO TANTO NO PUEDE SER ELIMINADO
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prestamo con ID "+idPrestamo+" no encontrado. No se ha podido eliminar");
		}
	}
}