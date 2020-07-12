package mx.uam.tsis2020.prestamax.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity//indica que hay que persistir en BD
@EqualsAndHashCode
public class Prestamo {
	
	@Id 
	@GeneratedValue //AUTOGENERA UN ID UNICO
	private Integer idPrestamo;
	
	@NotNull
	@ApiModelProperty(notes="id del cliente", required=true)
	private Integer idCliente;
	
	@NotNull
	@ApiModelProperty(notes="id del empleado", required=true)
	private Integer idEmpleado;
	
	@NotBlank
	@ApiModelProperty(notes="fecha del prestamo", required=true)
	private String fecha;
	
	@NotBlank
	@ApiModelProperty(notes="fecha limite para pagar el prestamo", required=true)
	private String fechaLimite;
	
	@NotNull
	@ApiModelProperty(notes="monto del prestamo", required=true)
	private Double cantidad;
	
	@NotNull
	@ApiModelProperty(notes="tasa de interes del prestamo", required=true)
	private Double tasaInteres;
	
	@NotBlank
	@ApiModelProperty(notes="dia de cada pago del prestamo", required=true)
	private String diaPago;
	
	@NotNull
	@ApiModelProperty(notes="cantidad de cada pago del prestamo", required=true)
	private Double cantidaPago;	
}
