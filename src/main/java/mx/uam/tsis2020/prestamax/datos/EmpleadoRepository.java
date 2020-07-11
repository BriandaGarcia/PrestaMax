package mx.uam.tsis2020.prestamax.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis2020.prestamax.negocio.modelo.Empleado;



public interface EmpleadoRepository extends CrudRepository<Empleado,Integer>{//identidad y llave primeria
	
	   
}
