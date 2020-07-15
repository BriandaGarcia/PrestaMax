package mx.uam.tsis2020.prestamax.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador Web
 * @author Brianda Garcia
 *
 */
@Controller
@Slf4j
public class MainController {

	@GetMapping("/")
	public String index(){
		log.info("Se invocó el método index()");
		return "index";
	}
	
	@GetMapping("/login")
	public String login(){
		log.info("Se invoco el login");
		return "login";
	}
	/**Metodo que manda al administrador a la vista que le corresponde**/
	@GetMapping("/administracion")
	public String administracion(){
		log.info("Se invoco el login");
		return "administracion";
	}
	@GetMapping("/PaneldeControl")
	public String panel() {
		return "PaneldeControl";
	}
	/**Metodo que manda al empleado a su pagina correspondiente**/
	@GetMapping("/empleado")
	public String empleado(){
		log.info("Se invoco el login");
		return "poner el nombre de la pagina del empleado aqui";
	}
	@GetMapping("/agregarEmpleado")
	public String createEmployee() {
		return "agregarEmpleado";
	}
	@GetMapping("/VerEmpleados")
	public String verEmpleados() {
		return "Empleados";
	}
	
	
	
	@RequestMapping("/prestamos/id")
	@ResponseBody
	public String prestamosId(){
		return "Pagina prestamo";
	}
	
	@RequestMapping("/pagos")
	@ResponseBody
	public String pagos(){
		return "Pagina pagos";
	}
	
	@RequestMapping("/pagos/id")
	@ResponseBody
	public String pagosId(){
		return "Pagina pago";
	}
	
	@RequestMapping("/cliente")
	@ResponseBody
	public String cliente(){
		return "Pagina clientes";
	}
	
	@RequestMapping("/cliente/id")
	@ResponseBody
	public String clienteId(){
		return "Pagina cliente";
	}
	
	
	
	@RequestMapping("/empleado/id")
	@ResponseBody
	public String empleadoId(){
		return "Pagina empleado";
	}
}
