package mx.uam.tsis2020.prestamax.negocio;

import java.util.Optional;

import org.hibernate.validator.cfg.context.ReturnValueTarget;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis2020.prestamax.datos.ClienteRepository;
import mx.uam.tsis2020.prestamax.datos.EmpleadoRepository;
import mx.uam.tsis2020.prestamax.datos.PrestamoRepository;
import mx.uam.tsis2020.prestamax.negocio.modelo.Cliente;
import mx.uam.tsis2020.prestamax.negocio.modelo.Empleado;
import mx.uam.tsis2020.prestamax.negocio.modelo.Prestamo;

/**
 * Clase que contiene la logica de negocio del manejo de prestamos
 * @author Brianda Garcia
 *
 */
@Service
@Slf4j
public class PrestamoService {
	
	@Autowired
	private PrestamoRepository prestamoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	/**
	 * Permite crear nuevos prestamos
	 * @param nuevoPrestamo El prestamo que se desea almacenar en la BD. Es de tipo Prestamo
	 * @return El prestamo que se acaba de crear si fue exitosa la creacion, null de lo contrario
	 */
	public Prestamo create(Prestamo nuevoPrestamo) {
	    
		//Valida que el cliente exista
		Optional<Cliente> cliente= clienteRepository.findById(nuevoPrestamo.getIdCliente());
		//Valida que el empleado exista
		Optional<Empleado> empleado= empleadoRepository.findById(nuevoPrestamo.getIdEmpleado());
		
		if(cliente.isPresent() && empleado.isPresent()) {
			if(validaStatusCliente(cliente.get().getStatus())) {
				if(validaCantidad(cliente.get().getSalario(), nuevoPrestamo.getCantidad())) {
					if(validaTasaInteres(nuevoPrestamo.getTasaInteres())) {
						if(validaNumeroPagos(nuevoPrestamo.getNumeroPagos())) {
							if(validaDiaPago(nuevoPrestamo.getDiaPago())) {
								if(validaCantidadPago(nuevoPrestamo)) {
									if(validaPenalizacion(nuevoPrestamo.getCantidad(), nuevoPrestamo.getPenalizacionDia())) {
										if(nuevoPrestamo.getRecargos() == 0) {
											return prestamoRepository.save(nuevoPrestamo);
										} else {
											return null;
										}
									} else {
										return null;
									}
								} else {
									return null;
								}
							} else {
								return null;
							}
						} else {
							return null;
						}
					} else {
						return null;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Recupera todos los prestamos existentes en la BD
	 * @return Regresa una lista con todos los prestamos de la BD. Son de tipo Prestamo
	 */
	public Iterable <Prestamo> retrieveAll(){
		return prestamoRepository.findAll();
    }
	
	/**
	 * Recupera un prestamo existente en la BD identificado por su ID
	 * @param idPrestamo ID del prestamo que se desea recuperar de la BD
	 * @return Regresa el prestamo deseado. 
	 */
	public Optional<Prestamo> retrieve(Integer idPrestamo){
		return prestamoRepository.findById(idPrestamo);
	}
	
	/**
	 * Actualiza un prestamo existente en la BD
	 * @param idPrestamo ID del prestamo que se desea actualizar
	 * @param prestamoActualizado Información actualizada del prestamo deseado
	 * @return Regresa el prestamo actualizado, null si el prestamo que se intenta actualizar no existe
	 */
	public Prestamo update(Integer idPrestamo, Prestamo prestamoActualizado) {
		return null;
	}
	
	/**
	 * Elimina una prestamo existente en la BD
	 * @param idPrestamo ID del prestamo que se desea eliminar
	 * @return True si el prestamo fue encontrado y eliminado, false en caso de no existir prestamo
	 */
	public boolean delete(Integer idPrestamo) {
		
		//Recupero el prestamo que se intenta eliminar
		Optional<Prestamo> prestamo = prestamoRepository.findById(idPrestamo);
		
		//Confirmo que existe
		if(prestamo.isPresent()) {
			prestamoRepository.deleteById(idPrestamo);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validaStatusCliente(String status) {
		if(status.equals("Bueno")) {
			return true;
		} else {
			return false;
		}		
	}
	
	public boolean validaCantidad(Double salario, Double cantidad) {
		
		if(salario <= 5000) {
			log.info("Voy a guardar un nuevo prestamo, con sueldo  <=5000 ");
			
			if(cantidad <= 20000) {
				log.info("Voy a guardar un nuevo prestamo  <=20000");
				return true;
			} else
				return false;
		} else {
			if(salario <= 15000) {
				log.info("Voy a guardar un nuevo prestamo, con sueldo entre <=15000 ");
			 
				if(cantidad <= 50000) {
					log.info("Voy a guardar un nuevo prestamo <=50000 ");
					return true;
				} else
					return false;
			} else {
				if(salario <= 30000 ) {
					log.info("Voy a guardar un nuevo prestamo, con sueldo <= 30000 ");
				 
					if(cantidad <= 100000) {
						log.info("Voy a guardar un nuevo prestamo >100000 ");
						return true;
					} else
						return false;
				} else {
					if(salario > 30000 ) {
						log.info("Voy a guardar un nuevo prestamo, con sueldo > 30000 ");
					 
						if(cantidad <= 150000) {
							log.info("Voy a guardar un nuevo prestamo >150000 ");
							return true;
						} else
							return false;
					} else
						return false;
				}
			}
		}		
	}
	
	public boolean validaTasaInteres(Double tasaInteres) {
		if(tasaInteres >= 5 && tasaInteres <= 10) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validaNumeroPagos(Integer numeroPagos) {
		if(numeroPagos == 6 || numeroPagos == 12 || numeroPagos == 18 || numeroPagos == 24) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean validaPenalizacion(Double cantidad, Integer penalizacionDia) {
		if(cantidad <= 20000 && penalizacionDia == 100) {
			return true;
		} else {
			if(cantidad <= 50000 && penalizacionDia == 300) {
				return true; 
			} else {
				if(cantidad <= 100000 && penalizacionDia == 500) {
					return true;
				} else {
					if(cantidad <= 150000 && penalizacionDia == 700) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
	}

	private boolean validaCantidadPago(Prestamo nuevoPrestamo) {
		Double prestamo = nuevoPrestamo.getCantidad();
		Double tasaInteres = (nuevoPrestamo.getTasaInteres())/100;
		Integer numeroPagos = nuevoPrestamo.getNumeroPagos();
		Double pago = (prestamo + (prestamo*tasaInteres))/numeroPagos;
		
		if(pago == nuevoPrestamo.getCantidadPago()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean validaDiaPago(Integer diaPago) {
		if(diaPago == 1) {
			return true;
		} else {
			return false;
		}
	}
}