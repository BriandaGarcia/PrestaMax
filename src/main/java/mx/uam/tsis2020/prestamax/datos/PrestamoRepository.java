package mx.uam.tsis2020.prestamax.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis2020.prestamax.negocio.modelo.Prestamo;



public interface PrestamoRepository extends CrudRepository<Prestamo,Integer>{//identidad y llave primeria
	
	   
}
