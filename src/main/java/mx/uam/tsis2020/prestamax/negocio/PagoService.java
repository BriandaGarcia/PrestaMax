package mx.uam.tsis2020.prestamax.negocio;

import java.util.Iterator;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis2020.prestamax.PrestamaxApplication;
import mx.uam.tsis2020.prestamax.datos.ClienteRepository;
import mx.uam.tsis2020.prestamax.datos.EmpleadoRepository;
import mx.uam.tsis2020.prestamax.datos.PagoRepository;
import mx.uam.tsis2020.prestamax.datos.PrestamoRepository;
import mx.uam.tsis2020.prestamax.negocio.modelo.Cliente;
import mx.uam.tsis2020.prestamax.negocio.modelo.Empleado;
import mx.uam.tsis2020.prestamax.negocio.modelo.Pago;
import mx.uam.tsis2020.prestamax.negocio.modelo.Prestamo;

/**
 * Clase que contiene la logica de negocio del manejo de pagos
 * @author Brianda Garcia
 *
 */
@Service
@Slf4j
public class PagoService {
	
	@Autowired
	private PrestamoRepository prestamoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private PagoRepository pagoRepository;

	/**
	 * Permite crear nuevos pagos
	 * @param nuevoPago El pago que desea almacenar en la BD. Es de Tipo Pago
	 * @return Si la creacion fue exitosa, devuelve el pago que se acaba de crear, null en caso contrario
	 */
	public Pago create(Pago nuevoPago) {
		if(validaExistePrestamo(nuevoPago.getIdPrestamo())) {
			log.info("Pase validacion de prestamo existente");
			if(validaExisteEmpleado(nuevoPago.getIdEmpleado())) {
				log.info("Pase validacion de empleado existente");
				if(validaDia(nuevoPago.getDia())) {
					log.info("Pase validaciones de dia de pago");
					Optional<Prestamo> prestamo = prestamoRepository.findById(nuevoPago.getIdPrestamo());
					if(validaCantidad(nuevoPago.getCantidad(), prestamo.get().getCantidadPago())) {
						log.info("Pase validaciones de cantidad del pago"); 
						nuevoPago = validaPenalizacion(nuevoPago, prestamo.get());
						log.info("Asigne penalizacion, actualice prestamo y actualice cliente");
						return pagoRepository.save(nuevoPago);
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
	 * Recupera todos los pagos existentes en la BD
	 * @return Regresa una lista con todos los pagos de la BD. Son de tipo Pago
	 */
	public Iterable <Pago> retrieveAll(){
		return pagoRepository.findAll();
    }
	
	/**
	 * Recupera un pago existente en la BD identificado por su ID
	 * @param idPago ID del pago que se desea recuperar de la BD
	 * @return Regresa el pago deseado. 
	 */
	public Optional<Pago> retrieve(Integer idPago){
		return pagoRepository.findById(idPago);
	}
	
	/**
	 * Actualiza un pago existente en la BD
	 * @param idPago ID del pago que se desea actualizar
	 * @param pagoActualizado Información actualizada del pago deseado
	 * @return Regresa el pago actualizado, null si el pago que se intenta actualizar no existe
	 */
	public Pago update(Integer idPago, Pago pagoActualizado) {
		if(validaExistePago(idPago)) {
			log.info("Pase validacion de pago existente");
			if(validaExistePrestamo(pagoActualizado.getIdPrestamo())) {
				log.info("Pase validacion de prestamo existente");
				if(validaExisteEmpleado(pagoActualizado.getIdEmpleado())) {
					log.info("Pase validacion de empleado existente");
					if(validaDia(pagoActualizado.getDia())) {
						log.info("Pase validaciones de dia de pago");
						Optional<Prestamo> prestamo = prestamoRepository.findById(pagoActualizado.getIdPrestamo());
						if(validaCantidad(pagoActualizado.getCantidad(), prestamo.get().getCantidadPago())) {
							log.info("Pase validaciones de cantidad del pago"); 
							pagoActualizado = validaPenalizacion(pagoActualizado, prestamo.get());
							log.info("Asigne penalizacion, actualice prestamo y actualice cliente");
							return pagoRepository.save(pagoActualizado);
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
	 * Elimina una pago existente en la BD
	 * @param idPago ID del pago que se desea eliminar
	 * @return True si el pago fue encontrado y eliminado, false en caso de no existir prestamo
	 */
	public boolean delete(Integer idPago) {
		//Recupero el pago que se intenta eliminar
		Optional<Pago> pago = pagoRepository.findById(idPago);
		
		//Confirmo que existe
		if(pago.isPresent()) {
			pagoRepository.deleteById(idPago);
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 *  Valida si el pago existe en la BD
	 * @param idPago Pago que se va a validar
	 * @return True si el pago existe, false si no
	 */
	public boolean validaExistePago(Integer idPago) {
		Optional<Pago> pago = pagoRepository.findById(idPago);
		if(pago.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Valida si el prestamo existe en la BD
	 * @param idPrestamo Prestamo que se va a validar
	 * @return True si el prestamo existe, false si no
	 */
	public boolean validaExistePrestamo(Integer idPrestamo) {
		Optional<Prestamo> prestamo = prestamoRepository.findById(idPrestamo);
		if(prestamo.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Valida si el empleado existe en la BD
	 * @param idEmpleado Empleado que se va a validar
	 * @return True si el empleado existe, false si no
	 */
	public boolean validaExisteEmpleado(Integer idEmpleado) {
		Optional<Empleado> empleado = empleadoRepository.findById(idEmpleado);
		if(empleado.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Valida que el dia de realizacion del pago sea correcto
	 * @param dia Dia en que se realiza el pago
	 * @return True si es correcto, false si no
	 */
	public boolean validaDia(Integer dia) {
		if(dia <= 31 && dia >= 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Valida que la csntidad del pago a realizar sea la correcta
	 * @param cantidadPago Cantidad que se va a pagar
	 * @param cantidadPagoPrestamo Cantidad que fue asignada en el prestamo
	 * @return True si las cantidades coinciden, false si no
	 */
	public boolean validaCantidad(Double cantidadPago, Double cantidadPagoPrestamo) {
		log.info("validaCantidad "+(cantidadPago.equals(cantidadPagoPrestamo)));
		if(cantidadPago.equals(cantidadPagoPrestamo)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verifica la penalizacion de cada pago y actualiza el monto de cada pago. Actualiza el status del cliente
	 * @param pago pago a asignar la penalizacion
	 * @param prestamo prestamo en el que se actualizara la cantidad de cada pago
	 * @return Devuelve el pago con la penalizacion adecuada
	 */
	public Pago validaPenalizacion(Pago pago, Prestamo prestamo) {
		Optional<Cliente> clienteOpt = clienteRepository.findById(prestamo.getIdCliente());
		Cliente cliente = clienteOpt.get();
		Integer dia = pago.getDia();
		Double recargos = 0.0;
		
		log.info("dia equals 1 "+dia.equals(1));
		
		if(dia.equals(1)) {
			cliente.setStatus("Bueno");
			clienteRepository.save(cliente);
			pago.setPenalizacion(recargos);
			return pago;
		} else {
			cliente.setStatus("Malo");
			clienteRepository.save(cliente);
			
			Integer diasRetraso = dia - 1;
			recargos = (double) (diasRetraso * prestamo.getPenalizacionDia());
			Integer recargosTotal = (int) (recargos + prestamo.getRecargos());
			prestamo.setRecargos(recargosTotal);
			
			Iterable<Pago> pagos = pagoRepository.findAll();
			Iterator<Pago> pagosIt = pagos.iterator();

			Integer numeroPagosRealizados = 1;
			
			while(pagosIt.hasNext()) {
				if(prestamo.getIdPrestamo().equals(pagosIt.next().getIdPrestamo())) {
					numeroPagosRealizados++;
					log.info(" if pagos realizados "+numeroPagosRealizados);
				}
				log.info("despues if pagos realizados "+numeroPagosRealizados);
			}
			log.info("Al salir del while pagos realizados "+numeroPagosRealizados);
			
			Integer pagosRestantes = prestamo.getNumeroPagos() - numeroPagosRealizados;
			log.info("Pagos restantes = 0 "+(pagosRestantes == 0));
			
			if(pagosRestantes == 0) {
				prestamo.setCantidadPago(recargos);
			} else {
				Double nuevoPago = prestamo.getCantidadPago() + (recargos/pagosRestantes);
				prestamo.setCantidadPago(nuevoPago);
			}
			
			prestamoRepository.save(prestamo);
			pago.setPenalizacion(recargos);
			return pago;
		}
	}
}
