package mx.uam.tsis2020.prestamax.negocio;

import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis2020.prestamax.PrestamaxApplication;
import mx.uam.tsis2020.prestamax.datos.EmpleadoRepository;
import mx.uam.tsis2020.prestamax.negocio.modelo.Empleado;



@Service
@Slf4j
public class EmpleadoService {
	
	
	@Autowired
	private EmpleadoRepository  empleadoRepository;
	
	public Empleado create(Empleado nuevoEmpleado) {
		
		log.info("Voy a guardar al empleado "+nuevoEmpleado);
			
		Empleado returnempleado =  empleadoRepository.save(nuevoEmpleado);
		
		log.info("Voy a regresar al cliente "+returnempleado);
			
		return returnempleado;
	}

public Iterable <Empleado> retrieveAll()
{
	return empleadoRepository.findAll();
}

//recupera empleado por id
public Empleado retieve(Integer idEmpleado)
	{
	  
		Optional<Empleado>  empleadofind =empleadoRepository.findById(idEmpleado);
		
		if(empleadofind.isPresent())
		{
			 return empleadofind.get();
		}else
			return null;
	}

public Empleado updateEmpleado(Integer idEmpleado, Empleado actualizaEmpleado)
	{
		 Optional<Empleado>  empleadofind = empleadoRepository.findById(idEmpleado);
		if(empleadofind.isPresent())
		{
			Empleado empleado = new Empleado();
		
			empleado.setApellidoPaterno(actualizaEmpleado.getApellidoPaterno());
			empleado.setApellidoMaterno(actualizaEmpleado.getApellidoMaterno());
			empleado.setNombre(actualizaEmpleado.getNombre());
			empleado.setPassword(actualizaEmpleado.getPassword());
			
			Empleado actualizadoEmpleado =empleadoRepository.save(actualizaEmpleado);
		
		return actualizadoEmpleado;
		}
		else
		{
			return null;
		}
	}

   public boolean delete(Integer idEmpleado)
   { 
	 Optional<Empleado>  empleadofind =empleadoRepository.findById(idEmpleado);
	
	 if(empleadofind.isPresent())
	 {
	  empleadoRepository.deleteById(idEmpleado);
	  return true;
	 }
	 else
		 return false;
  }


}
