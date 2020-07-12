package mx.uam.tsis2020.prestamax.negocio;

import java.util.Optional;

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
 * 
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
	 * 
	 * @param nuevoPrestamo
	 * @return
	 */
	public Prestamo create(Prestamo nuevoPrestamo) {
	    
		//Valida que el cliente exista
		Optional<Cliente> cliente= clienteRepository.findById(nuevoPrestamo.getIdCliente());
		//Valida que el empleado exista
		Optional<Empleado> empleado= empleadoRepository.findById(nuevoPrestamo.getIdEmpleado());
	
		log.info("recupere el idcliente"+cliente);
		log.info("recupere empleado"+empleado);
		
		Prestamo prestamo;
		
		if(cliente.isPresent() && empleado.isPresent()) {
			Double clienteSalary = clienteService.retrieveSalary(cliente.get().getIdCliente());
			log.info("recupero el salario "+clienteSalary);
			
			if(clienteSalary <= 10000) {
				log.info("Voy a guardar un nuevo prestamo, con sueldo  <=10000 "+nuevoPrestamo);
				
				if(nuevoPrestamo.getCantidad() <= 15000) {
					log.info("Voy a guardar un nuevo prestamo  <=15000"+nuevoPrestamo);
				
					prestamo =  prestamoRepository.save(nuevoPrestamo);
				} else
					return null;
			} else {
				if(clienteSalary <= 30000 ) {
					log.info("Voy a guardar un nuevo prestamo, con sueldo entre <=30000 "+nuevoPrestamo);
				 
					if(nuevoPrestamo.getCantidad() <= 50000) {
						log.info("Voy a guardar un nuevo prestamo <=50000 "+nuevoPrestamo);
					
						prestamo = prestamoRepository.save(nuevoPrestamo);
					} else
						return null;
				} else {
					if(clienteSalary > 30000 ) {
						log.info("Voy a guardar un nuevo prestamo, con sueldo >30000 "+nuevoPrestamo);
						
						prestamo = prestamoRepository.save(nuevoPrestamo);
					 
						if(nuevoPrestamo.getCantidad() > 50000) {
							log.info("Voy a guardar un nuevo prestamo >50000 "+nuevoPrestamo);
								
							prestamo = prestamoRepository.save(nuevoPrestamo);	 
						}
					} else
						return null;
				}
			}
		} else
			return null;
		
		return prestamo;
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
		
		//Recupero el prestamos que se intentá actualizar
		Optional<Prestamo> prestamo = prestamoRepository.findById(idPrestamo);
		
		//Confirmo que el prestamo existe
		if(prestamo.isPresent()) {
			
			//Valida que el cliente exista
			Optional<Cliente> cliente= clienteRepository.findById(prestamoActualizado.getIdCliente());
			//Valida que el empleado exista
			Optional<Empleado> empleado= empleadoRepository.findById(prestamoActualizado.getIdEmpleado());
			
			if(cliente.isPresent() && empleado.isPresent()) {
				Double clienteSalary = clienteService.retrieveSalary(cliente.get().getIdCliente());
				
				if(clienteSalary <= 10000) {
					log.info("Voy a actualizar prestamo, con sueldo  <=10000 "+prestamoActualizado);
					
					if(prestamoActualizado.getCantidad() <= 15000) {
						log.info("Voy a actualizar prestamo  <=15000"+prestamoActualizado);
					
						prestamoActualizado =  prestamoRepository.save(prestamoActualizado);
					} else
						return null;
				} else {
					if(clienteSalary <= 30000 ) {
						log.info("Voy a guardar actualizar, con sueldo entre <=30000 "+prestamoActualizado);
					 
						if(prestamoActualizado.getCantidad() <= 50000) {
							log.info("Voy a actualizar prestamo <=50000 "+prestamoActualizado);
						
							prestamoActualizado = prestamoRepository.save(prestamoActualizado);
						} else
							return null;
					} else {
						if(clienteSalary > 30000 ) {
							log.info("Voy a actualizar prestamo, con sueldo >30000 "+prestamoActualizado);
							
							prestamoActualizado = prestamoRepository.save(prestamoActualizado);
						 
							if(prestamoActualizado.getCantidad() > 50000) {
								log.info("Voy a actualizar prestamo >50000 "+prestamoActualizado);
									
								prestamoActualizado = prestamoRepository.save(prestamoActualizado);	 
							}
						} else
							return null;
					}
				}
			} else
				return null;
			
			return prestamoActualizado;
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
}