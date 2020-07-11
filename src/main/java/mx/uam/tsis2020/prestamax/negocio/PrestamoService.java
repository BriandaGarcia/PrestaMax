package mx.uam.tsis2020.prestamax.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis2020.prestamax.datos.PrestamoRepository;
import mx.uam.tsis2020.prestamax.negocio.modelo.Cliente;
import mx.uam.tsis2020.prestamax.negocio.modelo.Empleado;
import mx.uam.tsis2020.prestamax.negocio.modelo.Prestamo;

@Service
@Slf4j
public class PrestamoService {
	
	@Autowired
	private PrestamoRepository prestamoRepository;
	
	@Autowired
	private EmpleadoService  empleadoService;
	
	@Autowired
	private ClienteService clienteService;
	
public Prestamo create(Prestamo nuevoPrestamo,Integer idCliente, Integer idEmpleado) {
		
		// Regla de negocio: no se puede dar de alta un prestamo con el mismo id,
	   //debe existir un cliente y un empleado
	    
	Cliente cliente= clienteService.retieve(idCliente);
	Empleado empleado= empleadoService.retieve(idEmpleado);
	
	Optional <Prestamo> prestamo = prestamoRepository.findById(nuevoPrestamo.getIdPrestamo());
	
	 Double clientesalary = clienteService.retieveSalary(idCliente);
		log.info("recupero el salario "+clientesalary);
		
		log.info("recupere el idcliente"+cliente);
		log.info("recupere empleado"+empleado);
		log.info("tengo prestamo= "+prestamo);
		
		Prestamo returnprestamo=null;
		
		if(cliente!=null && empleado!=null && !prestamo.isPresent())
		{
			if( clientesalary<=10000) 
				
			{
				log.info("Voy a guardar un nuevo prestamo, con sueldo  <=10000 "+nuevoPrestamo);
				if(nuevoPrestamo.getCantidad()>6000 || nuevoPrestamo.getCantidad()<=15000)
				{
					log.info("Voy a guardar un nuevo prestamo  <=15000"+nuevoPrestamo);
				
				returnprestamo=  prestamoRepository.save(nuevoPrestamo);
				}else
					return null;
			}else
			{
			 if(clientesalary<=30000 )
			 {
				 log.info("Voy a guardar un nuevo prestamo, con sueldo entre <=30000 "+nuevoPrestamo);
				 
				 if(nuevoPrestamo.getCantidad()>12000 || nuevoPrestamo.getCantidad()<=50000)
				 {	 
					 log.info("Voy a guardar un nuevo prestamo <=50000 "+nuevoPrestamo);
					
					 returnprestamo= prestamoRepository.save(nuevoPrestamo);
					
				 }else
					 return null;
			 }else {
				 if(clientesalary>30000 )
				 {
					 log.info("Voy a guardar un nuevo prestamo, con sueldo >30000 "+nuevoPrestamo);
						 returnprestamo= prestamoRepository.save(nuevoPrestamo);
						 if(nuevoPrestamo.getCantidad()>50000)
						 {
							 log.info("Voy a guardar un nuevo prestamo >50000 "+nuevoPrestamo);
								
							 returnprestamo= prestamoRepository.save(nuevoPrestamo);
							 
						 }
				 }else
					 return null;
			    }
			}
		}
		else {
		
		return null;
		}
		return returnprestamo;
		
}
   public Iterable <Prestamo> retrieveAll()
   {
	return prestamoRepository.findAll();
    }

}
