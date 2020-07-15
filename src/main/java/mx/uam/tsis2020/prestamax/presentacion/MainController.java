package mx.uam.tsis2020.prestamax.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis2020.prestamax.negocio.ClienteService;
import mx.uam.tsis2020.prestamax.negocio.PrestamoService;
import mx.uam.tsis2020.prestamax.negocio.modelo.Cliente;
import mx.uam.tsis2020.prestamax.negocio.modelo.Prestamo;;

/**
 * Controlador Web
 * @author Brianda Garcia
 *
 */
@Controller
@Slf4j
public class MainController {

	@Autowired
	private PrestamoService prestamoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/")
	public String main() {
		return "prestamos/prestamoMenu";
	}//fin main
	
	@RequestMapping("/crear")
	public String crearPrestamo() {
		return "prestamos/formPrestamo";
	}//fin crearPrestamo
	
	@GetMapping("/listPrestamos")
	public String listPrestamos(Model model) {
		
		model.addAttribute("prestamos", prestamoService.retrieveAll());
		return "prestamos/listPrestamos";
	}
	
	@GetMapping("/eliminar/{id}")
	public String listPrestamos(@PathVariable("id") Integer idPrestamo, RedirectAttributes attributes) {
		if(prestamoService.delete(idPrestamo)) {
			/*Manda mensaje en la pantalla para confirmar que el prestamo ha sido eliminado con exito*/
			attributes.addFlashAttribute("msg", "Prestamo eliminado");
		}
		else
			attributes.addFlashAttribute("error", "Prestamo no encontrado para eliminar");
		return "redirect:/listPrestamos";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalles(@PathVariable("id") Integer idPrestamo, Model model) {
		Prestamo prestamo = prestamoService.retrieve(idPrestamo).get();
		Cliente cliente = clienteService.retrieve(prestamo.getIdCliente());
		model.addAttribute("prestamo", prestamo);
		model.addAttribute("cliente", cliente);
		return "prestamos/detallePrestamo";
	}
	
	@GetMapping("/buscaPrestamo")
	public String buscaPrestamo() {
		return "prestamos/buscaPrestamo";
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
