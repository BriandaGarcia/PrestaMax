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
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity//indica que hay que persistir en BD
public class Cliente {
	
	@Id //indica la llave primaria
	@GeneratedValue //AUTOGENERA UN ID UNICO
	private Integer idCliente;
	
	@NotBlank
	@ApiModelProperty(notes="Nombre del cliente", required=true)
	private String nombre;
	
	@NotBlank
	@ApiModelProperty(notes="Apellido paterno del cliente", required=true)
	private String apellidoPaterno;
	
	@NotBlank
	@ApiModelProperty(notes="Apellido materno del cliente", required=true)
	private String apellidoMaterno;
	
	@NotNull
	@ApiModelProperty(notes="Salario del cliente", required=true)
	private Double salario;
	
	@NotBlank
	@ApiModelProperty(notes="Telefono del cliente", required=true)
	private String telefono;
	
	@NotBlank
	@ApiModelProperty(notes="Clave de INE del cliente", required=true)
	private String ine;
	
	@NotBlank
	@ApiModelProperty(notes="Direccion del cliente", required=true)
	private String direccion;
	
	@NotBlank
	@ApiModelProperty(notes="status del cliente", required=true)
	private String status;
	
}
