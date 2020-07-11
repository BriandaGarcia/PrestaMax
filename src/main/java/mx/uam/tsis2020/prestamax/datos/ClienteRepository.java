package mx.uam.tsis2020.prestamax.datos;


import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis2020.prestamax.negocio.modelo.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente,Integer>{//identidad y llave primeria
	
	   
}
