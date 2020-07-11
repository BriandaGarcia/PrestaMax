package mx.uam.tsis2020.prestamax.negocio.modelo;

import javax.persistence.Entity;
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
public class Pago {
	
	@NotNull
	@ApiModelProperty(notes="id del pago", required=true)
	@Id 
	private Integer idPago;
	
	@NotBlank
	private String Fecha;
	
	@NotNull
	private Double cantidad;

}
