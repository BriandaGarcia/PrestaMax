package mx.uam.tsis2020.prestamax.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis2020.prestamax.negocio.modelo.Pago;


public interface PagoRepository extends CrudRepository<Pago,Integer>{//identidad y llave primeria
	
	   
}
