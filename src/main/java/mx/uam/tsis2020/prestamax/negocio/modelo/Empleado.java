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
public class Empleado {
	
	@Id 
	@GeneratedValue //AUTOGENERA UN ID UNICO
	private Integer idEmpleado;
	
	@NotBlank
	@ApiModelProperty(notes="Nombre del empleado", required=true)
	private String nombre;
	
	@NotBlank
	@ApiModelProperty(notes="Apellido paterno del empleado", required=true)
	private String apellidoPaterno;
	
	@NotBlank
	@ApiModelProperty(notes="Apellido materno del empleado", required=true)
	private String apellidoMaterno;
	
	@NotNull
	@ApiModelProperty(notes="Salario del empleado", required=true)
	private Double salario;
	
	@NotBlank
	@ApiModelProperty(notes="Cargo del empleado", required=true)
	private String cargo;
	
	@NotBlank
	@ApiModelProperty(notes="Password del empleado", required=true)
	private String password;


}
