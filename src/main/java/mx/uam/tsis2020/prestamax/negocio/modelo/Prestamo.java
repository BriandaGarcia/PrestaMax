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
	
	@NotNull
	@ApiModelProperty(notes="monto del prestamo", required=true)
	private Double cantidad;
	
	@NotNull
	@ApiModelProperty(notes="tasa de interes del prestamo", required=true)
	private Double tasaInteres;
	
	@NotNull
	@ApiModelProperty(notes="numero de pagos del prestamo", required=true)
	private Integer numeroPagos;
	
	@NotNull
	@ApiModelProperty(notes="dia de cada pago del prestamo", required=true)
	private Integer diaPago;
	
	@NotNull
	@ApiModelProperty(notes="cantidad de cada pago del prestamo", required=true)
	private Double cantidadPago;	
	
	@NotNull
	@ApiModelProperty(notes="Penalizacion al dia por pago tardio", required=true)
	private Integer penalizacionDia;
	
	@NotNull
	@ApiModelProperty(notes="Total del cargo por penalizacion acumulado", required=true)
	private Integer recargos;
	
}
