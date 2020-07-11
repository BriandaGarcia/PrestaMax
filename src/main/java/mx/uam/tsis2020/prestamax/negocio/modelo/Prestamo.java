package mx.uam.tsis2020.prestamax.negocio.modelo;

import java.util.Date;

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
public class Prestamo {
	
	@NotNull
	@ApiModelProperty(notes="id del prestamo", required=true)
	@Id 
	private Integer idPrestamo;
	
	@NotBlank
	private String Fecha;
	
	@NotBlank
	private String FechaLimite;
	
	@NotNull
	private Double Cantidad;
	
	@NotNull
	private Double TazadeInteres;
	
	@NotNull
	private Double Penalizacion;
	
	@NotBlank
	private String DiadePago;
	
	@NotNull
	private Double CantidadePago;
	
	

}
