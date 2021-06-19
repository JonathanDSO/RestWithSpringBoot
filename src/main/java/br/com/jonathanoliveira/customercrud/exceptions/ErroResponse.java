package br.com.jonathanoliveira.customercrud.exceptions;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ErroResponse implements Serializable {

	private static final long serialVersionUID = -101863202902147377L;

	private Date timestamp;
	private String message;
	private String details;

}
