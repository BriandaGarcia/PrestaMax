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
	
	/**Cambios hechos por hernan**/
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
	/**hasta aqui acaban los cambios hechos por hernan**/
	
	@GetMapping("/prestamo")
	public String main() {
		return "prestamos/prestamoMenu";
	}//fin main
	
	@RequestMapping("/prestamo/crear")
	public String crearPrestamo() {
		return "prestamos/formPrestamo";
	}//fin crearPrestamo
	
	@GetMapping("/prestamo/listPrestamos")
	public String listPrestamos(Model model) {
		model.addAttribute("prestamos", prestamoService.retrieveAll());
		return "prestamos/listPrestamos";
	}
	
	@GetMapping("/prestamo/eliminar/{id}")
	public String listPrestamos(@PathVariable("id") Integer idPrestamo, RedirectAttributes attributes) {
		if(prestamoService.delete(idPrestamo)) {
			/*Manda mensaje en la pantalla para confirmar que el prestamo ha sido eliminado con exito*/
			attributes.addFlashAttribute("msg", "Prestamo eliminado");
		}
		else
			attributes.addFlashAttribute("error", "Prestamo no encontrado para eliminar");
		return "redirect:/prestamo/listPrestamos";
	}
	
	@GetMapping("/prestamo/detalle/{id}")
	public String detalles(@PathVariable("id") Integer idPrestamo, Model model) {
		Prestamo prestamo = prestamoService.retrieve(idPrestamo).get();
		Cliente cliente = clienteService.retrieve(prestamo.getIdCliente());
		model.addAttribute("prestamo", prestamo);
		model.addAttribute("cliente", cliente);
		return "prestamos/detallePrestamo";
	}
	
	@GetMapping("/prestamo/buscaPrestamo")
	public String buscaPrestamo() {
		return "prestamos/buscaPrestamo";
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
	public String cliente(){
		return "clientes/clienteMenu";
	}
	
	@GetMapping("/cliente/crear")
	public String formCliente() {
		return "clientes/formCliente";
	}
	
	@GetMapping("/cliente/buscar")
	public String detalle() {
		return "clientes/detalleCliente";
	}
	
	@GetMapping("/cliente/list")
	public String listClientes(Model model) {
		model.addAttribute("clientes", clienteService.retrieveAll());
		return "clientes/listCliente";
	}
	
	@RequestMapping("/cliente/eliminar/{id}")
	public String clienteId(@PathVariable("id") Integer idCliente, RedirectAttributes attributes) {
		if(clienteService.delete(idCliente)) {
			/*Manda mensaje en la pantalla para confirmar que el prestamo ha sido eliminado con exito*/
			attributes.addFlashAttribute("msg", "Cliente eliminado");
		}
		else
			attributes.addFlashAttribute("error", "Cliente no encontrado para eliminar");
		return "redirect:/cliente/list";		
	}
	
	
	
	@RequestMapping("/empleado/id")
	@ResponseBody
	public String empleadoId(){
		return "Pagina empleado";
	}
}
