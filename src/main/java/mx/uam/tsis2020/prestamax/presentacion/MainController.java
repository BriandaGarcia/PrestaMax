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
	
	@RequestMapping("/prestamos")
	@ResponseBody
	public String prestamos(){
		return "Pagina prestamos";
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
	
	@RequestMapping("/empleado")
	@ResponseBody
	public String empleado(){
		return "Pagina empleados";
	}
	
	@RequestMapping("/empleado/id")
	@ResponseBody
	public String empleadoId(){
		return "Pagina empleado";
	}
}
