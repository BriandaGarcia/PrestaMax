package mx.uam.tsis2020.prestamax.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	/**
	 * Permite crear nuevos prestamos
	 * @param nuevoPrestamo El prestamo que se desea almacenar en la BD. Es de tipo Prestamo
	 * @return El prestamo que se acaba de crear si fue exitosa la creacion, null de lo contrario
	 */
	public Prestamo create(Prestamo nuevoPrestamo) {
		if(validaExisteCliente(nuevoPrestamo.getIdCliente())) {
			log.info("Pase validacion de cliente existente");
			if(validaExisteEmpleado(nuevoPrestamo.getIdEmpleado())) {
				log.info("Pase validacion de empleado existente");
				Optional<Cliente> cliente= clienteRepository.findById(nuevoPrestamo.getIdCliente());	
				if(validaStatusCliente(cliente.get().getStatus())) {
					log.info("Pase validaciones de status cliente");
					if(validaCantidad(cliente.get().getSalario(), nuevoPrestamo.getCantidad())) {
						log.info("Pase validaciones de cantidad del prestamo de acuerdo al salario");
						if(validaTasaInteres(nuevoPrestamo.getTasaInteres())) {
							log.info("Pase validaciones de la tasa de interes");
							if(validaNumeroPagos(nuevoPrestamo.getNumeroPagos())) {
								log.info("Pase validaciones de el numero de pagos");
								if(validaDiaPago(nuevoPrestamo.getDiaPago())) {
									log.info("Pase validaciones de dia de pago");
									if(validaCantidadPago(nuevoPrestamo)) {
										log.info("Pase validaciones de la cantidad de cada pago");
										if(validaPenalizacion(nuevoPrestamo.getCantidad(), nuevoPrestamo.getPenalizacionDia())) {
											log.info("Pase validaciones de la penalizacion asignada");
											if(nuevoPrestamo.getRecargos() == 0) {
												log.info("Pase validaciones de los recargos iniciales");
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
	 * @return Regresa el prestamo actualizado, null si el prestamo que se intenta actualizar no existe o tiene informacion erronea
	 */
	public Prestamo update(Integer idPrestamo, Prestamo prestamoActualizado) {
		if(validaExistePrestamo(idPrestamo)) {
			log.info("Pase validacion de prestamo existente");
			if(validaExisteCliente(prestamoActualizado.getIdCliente())) {
				log.info("Pase validacion de cliente existente");
				if(validaExisteEmpleado(prestamoActualizado.getIdEmpleado())) {
					log.info("Pase validacion de empleado existente");
					Optional<Cliente> cliente= clienteRepository.findById(prestamoActualizado.getIdCliente());	
					if(validaStatusCliente(cliente.get().getStatus())) {
						log.info("Pase validaciones de status cliente");
						if(validaCantidad(cliente.get().getSalario(), prestamoActualizado.getCantidad())) {
							log.info("Pase validaciones de cantidad del prestamo de acuerdo al salario");
							if(validaTasaInteres(prestamoActualizado.getTasaInteres())) {
								log.info("Pase validaciones de la tasa de interes");
								if(validaNumeroPagos(prestamoActualizado.getNumeroPagos())) {
									log.info("Pase validaciones de el numero de pagos");
									if(validaDiaPago(prestamoActualizado.getDiaPago())) {
										log.info("Pase validaciones de dia de pago");
										if(validaCantidadPago(prestamoActualizado)) {
											log.info("Pase validaciones de la cantidad de cada pago");
											if(validaPenalizacion(prestamoActualizado.getCantidad(), prestamoActualizado.getPenalizacionDia())) {
												log.info("Pase validaciones de la penalizacion asignada");
												if(prestamoActualizado.getRecargos() == 0) {
													log.info("Pase validaciones de los recargos iniciales");
													return prestamoRepository.save(prestamoActualizado);
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
			} else {
				return null;
			}
		} else {
			return null;
		}
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

	/**
	 * Valida si un prestamo existe en la BD
	 * @param idPrestamo Prestamo que se va a validar
	 * @return True si existe, false en caso contrario
	 */
	public boolean validaExistePrestamo(Integer idPrestamo) {
		//Recupero el prestamo que se intenta actualizar
		Optional<Prestamo> prestamo = prestamoRepository.findById(idPrestamo);
		//Verifico que el prestamo exista
		if(prestamo.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Valida si existe un cliente en la BD
	 * @param idCliente Cliente que se va a validar
	 * @return True si existe, false en caso contrario
	 */
	public boolean validaExisteCliente(Integer idCliente) {
		//Recupera al cliente
		Optional<Cliente> cliente= clienteRepository.findById(idCliente);		
		//Verifico que el cliente exista
		if(cliente.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Valida si existe un empleado en la BD 
	 * @param idEmpleado Empleado que se va a validar
	 * @return True si existe, false si no
	 */
	public boolean validaExisteEmpleado(Integer idEmpleado) {
		//Recupera al empleado
		Optional<Empleado> empleado= empleadoRepository.findById(idEmpleado);
		//Verifico que el empleado existe
		if(empleado.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Valida el estado de cliente
	 * @param status Estado del cliente
	 * @return True si el cliente tiene un buen estado, false si no
	 */
	public boolean validaStatusCliente(String status) {
		if(status.equals("Bueno")) {
			return true;
		} else {
			return false;
		}		
	}
	
	/**
	 * Valida que la cantidad del prestamo sea acorde al salario del cliente que solicita
	 * @param salario Salario del cliente solicitante
	 * @param cantidad Monto del prestamo solicitad
	 * @return True si el monto del prestamo es acorde al salario, false en caso contrario
	 */
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
	
	/** 
	 * Valida que la tasa de interes esté en el intervalo aceptado de la empresa
	 * @param tasaInteres La tasa de interes a validar
	 * @return True si la tasa de interes esta dentro de los parametro, false si no
	 */
	public boolean validaTasaInteres(Double tasaInteres) {
		if(tasaInteres >= 5 && tasaInteres <= 10) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Valida que el numero de pagos del prestamo sea aceptado por la empresa
	 * @param numeroPagos El numero de meses en que se va a pagar el cliente
	 * @return True si el numero de pagos es aeptado por la empresa, false en caso contrario
	 */
	public boolean validaNumeroPagos(Integer numeroPagos) {
		if(numeroPagos == 6 || numeroPagos == 12 || numeroPagos == 18 || numeroPagos == 24) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Valida que la penalizacion asignada sea acorde al monto total del prestamo
	 * @param cantidad Monto total del prestamo
	 * @param penalizacionDia Penalizacion asignada
	 * @return True si la penalizacion es acorde al monto, false si no
	 */
	public boolean validaPenalizacion(Double cantidad, Integer penalizacionDia) {
		if(cantidad <= 20000) {
			log.info("Voy a guardar un nuevo prestamo, con cantidad  <=20000 ");
			
			if(penalizacionDia == 100) {
				log.info("Voy a guardar una penalizacion  de 100");
				return true;
			} else
				return false;
		} else {
			if(cantidad <= 50000) {
				log.info("Voy a guardar un nuevo prestamo, con cantidad <=50000 ");
			 
				if(penalizacionDia == 300) {
					log.info("Voy a guardar una penalizacion de 300 ");
					return true;
				} else
					return false;
			} else {
				if(cantidad <= 100000 ) {
					log.info("Voy a guardar un nuevo prestamo, con cantidad <= 100000 ");
				 
					if(penalizacionDia == 500) {
						log.info("Voy a guardar una penalizacion de 500");
						return true;
					} else
						return false;
				} else {
					if(cantidad > 150000 ) {
						log.info("Voy a guardar un nuevo prestamo, con cantidad > 150000 ");
					 
						if(penalizacionDia == 700) {
							log.info("Voy a guardar una penalizacion de 700");
							return true;
						} else
							return false;
					} else
						return false;
				}
			}
		}
	}

	/**
	 * Valida que la cantidad de cada pago del prestamo sea correcta
	 * @param nuevoPrestamo Informacion del prestamo
 	 * @return True si la cantidad de cada pago es correcta, false si no
	 */
	public boolean validaCantidadPago(Prestamo nuevoPrestamo) {
		Double prestamo = nuevoPrestamo.getCantidad();
		Double tasaInteres = (nuevoPrestamo.getTasaInteres())/100;
		Integer numeroPagos = nuevoPrestamo.getNumeroPagos();
		Double pago = (prestamo + (prestamo*tasaInteres))/numeroPagos;
		
		log.info("pago "+pago+" cantidadPago "+nuevoPrestamo.getCantidadPago());
		
		if(pago.equals(nuevoPrestamo.getCantidadPago())){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida que el dia de pago sea el designado por la empresa
	 * @param diaPago El dia de pago indicado en el prestamo
	 * @return True si el dia de pago es correcto, false si no 
	 */
	public boolean validaDiaPago(Integer diaPago) {
		if(diaPago == 1) {
			return true;
		} else {
			return false;
		}
	}
}