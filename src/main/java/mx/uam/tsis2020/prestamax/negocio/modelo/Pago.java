package mx.uam.tsis2020.prestamax.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class Pago {
	
	@Id
	@GeneratedValue //AUTOGENERA UN ID UNICO
	private Integer idPago;
	
	@NotNull
	@ApiModelProperty(notes="id del prestamo del cual se está realizando el pago", required=true)
	private Integer idPrestamo;
	
	@NotNull
	@ApiModelProperty(notes="id del empleado que realiza el cobro del pago", required=true)
	private Integer idEmpleado;
	
	@NotNull
	@ApiModelProperty(notes="día en que se está realizando el pago", required=true)
	private Integer dia;

	@NotNull
	@ApiModelProperty(notes="mes en que se está realizando el pago", required=true)
	private Integer mes;
	
	@NotNull
	@ApiModelProperty(notes="año en que se está realizando el pago", required=true)
	private Integer año;
	
	@NotNull
	@ApiModelProperty(notes="cantidad del pago", required=true)
	private Double cantidad;
	
	@NotNull
	@ApiModelProperty(notes="penalizacion del pago tardio", required=true)
	private Double penalizacion;
}
