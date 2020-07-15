package mx.uam.tsis2020.prestamax.servicio;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import mx.uam.tsis2020.prestamax.negocio.EmpleadoService;
import mx.uam.tsis2020.prestamax.negocio.modelo.Empleado;

@RestController
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@ApiOperation
	(//documentacion del api
	  value = "Crear un nuevo Empleado",
	  notes="Permite crear un nuevo Empleado, cada empleado debe llevar un identidicador unico, de lo contrario no se puede dar crear"
	)	
	@PostMapping(path = "/empleados", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Empleado nuevoEmpleado) { // Validaciones
				
		
		Empleado empleado = empleadoService.create(nuevoEmpleado);
		
		if(empleado != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(empleado);			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se puede crear empleado");
		}
	

	}
	
	@ApiOperation
	(//documentacion del api
	  value = "Recupera todos los Empleados",
	  notes="Recupera todos los empleados que se crearon"
    )
    @GetMapping(path = "/empleados", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Empleado> empleados = empleadoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(empleados); 
		
	}
	
	@ApiOperation
	(//documentacion del api
	  value = "Recupera un Empleado por id",
	  notes="Recupera un Empleado por medio de un id existente para ver su información"
	)
	@GetMapping(path = "/empleados/{idEmpleado}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("idEmpleado") Integer idEmpleado) {
		
		
		Empleado empleado = empleadoService.retieve(idEmpleado);
		
		if(empleado != null) {
			return ResponseEntity.status(HttpStatus.OK).body(empleado);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado con id: "+idEmpleado +" no existe");
		}
		
		
	}
	
	@ApiOperation
	(//documentacion del api
	  value = "Actualiza Empleado",
	  notes="Actualiza los datos del Empleado por medio de su id (idEmpleado)"
	)
	@PutMapping(path = "empleados/{idEmpleado}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@PathVariable("idEmpleado") Integer idEmpleado,@RequestBody Empleado actualizaEmpleado)
	{
 		Empleado empleado= empleadoService.updateEmpleado(idEmpleado, actualizaEmpleado);
 		if(empleado!=null)
 		{
 	      return ResponseEntity.status(HttpStatus.OK).body(empleado);
 		}
 		else
 		{
 			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
 		}
	}
	
	@ApiOperation
	(//documentacion del api
	  value = "Elimina Empleado",
	  notes="Elimina un Empleado por medio de su id"
	)	
    @DeleteMapping(path = "empleados/{idEmpleado}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity <?> delete(@PathVariable("idEmpleado") Integer idEmpleado)
   {
	   boolean empleadoeliminado=empleadoService.delete(idEmpleado);
		
	   if(empleadoeliminado ==true)
         return ResponseEntity.status(HttpStatus.OK).build();
	   else
		   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se puede eliminar empleado, el id no existe");
   }

}
