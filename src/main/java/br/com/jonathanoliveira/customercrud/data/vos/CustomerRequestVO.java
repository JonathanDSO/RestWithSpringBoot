package br.com.jonathanoliveira.customercrud.data.vos;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestVO {

	@NotEmpty
	@NotNull
	private String name;

	@NotEmpty
	@NotNull
	@CPF()
	@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}", message = "The cpf must match this format: xxx.xxx.xxx-xx")
	private String cpf;

	@NotNull
	private Date birthDate;
}
