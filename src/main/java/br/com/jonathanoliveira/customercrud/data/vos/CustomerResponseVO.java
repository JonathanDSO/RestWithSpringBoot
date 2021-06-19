package br.com.jonathanoliveira.customercrud.data.vos;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseVO {

	private Integer id;
	private String name;
	private String cpf;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	public Integer getAge() {
		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar birth = new GregorianCalendar();
		if (birthDate != null) {
			birth.setTime(birthDate);
		}
		return today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
	}

}
