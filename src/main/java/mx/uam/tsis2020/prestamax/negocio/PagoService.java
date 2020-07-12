package mx.uam.tsis2020.prestamax.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
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
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private PagoRepository pagoRepository;
	
	@Autowired
	private ClienteService clienteService;

	/**
	 * Permite crear nuevos pagos
	 * @param nuevoPago El pago que desea almacenar en la BD. Es de Tipo Pago
	 * @return Si la creacion fue exitosa, devuelve el pago que se acaba de crear, null en caso contrario
	 */
	public Pago create(Pago nuevoPago) {
	    
		//Valida que el prestamo exista
		Optional<Prestamo> prestamo= prestamoRepository.findById(nuevoPago.getIdPrestamo());
		//Valida que el empleado exista
		Optional<Empleado> empleado= empleadoRepository.findById(nuevoPago.getIdEmpleado());
	
		log.info("recupere el prestamo"+prestamo);
		log.info("recupere empleado"+empleado);
		
		Pago pago = null;
		return pago;
		
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
		
		return pagoActualizado;
		
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
}
