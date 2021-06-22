package br.com.jonathanoliveira.customercrud.facades;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.jonathanoliveira.customercrud.data.entities.Customer;
import br.com.jonathanoliveira.customercrud.data.vos.CustomerRequestVO;
import br.com.jonathanoliveira.customercrud.data.vos.CustomerResponseVO;
import br.com.jonathanoliveira.customercrud.services.CustomerService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = CustomerFacade.class)
public class CustomerFacadeTest {

	@InjectMocks
	private CustomerFacade customerFacade;

	@Mock
	private CustomerService customerService;

	@Test
	public void insert() throws ParseException {
		CustomerRequestVO customerDto = new CustomerRequestVO();
		when(customerService.save(ArgumentMatchers.any())).thenReturn(payload_Customer());
		CustomerResponseVO result = customerFacade.insert(customerDto);
		assertEquals(payload_CustomerResponseVO(), result);
	}

	@Test
	public void update() throws ParseException {
		CustomerRequestVO customerDto = new CustomerRequestVO();
		when(customerService.findById(ArgumentMatchers.anyInt())).thenReturn(payload_Customer());
		when(customerService.save(ArgumentMatchers.any())).thenReturn(payload_Customer());
		CustomerResponseVO result = customerFacade.update(1, customerDto);
		assertEquals(payload_CustomerResponseVO(), result);
	}

	@Test
	public void findAll() throws ParseException {
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(payload_Customer());
		when(customerService.findAll()).thenReturn(customers);
		List<CustomerResponseVO> result = customerFacade.findAll();
		List<CustomerResponseVO> customerResponseVOs = new ArrayList<>();
		customerResponseVOs.add(payload_CustomerResponseVO());
		assertEquals(customerResponseVOs, result);
	}
	
	@Test
	public void findAll_Pageable() throws ParseException {
		Pageable pageable = PageRequest.of(0, 10);
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(payload_Customer());
		when(customerService.findAll(pageable)).thenReturn(customers);
		List<CustomerResponseVO> result = customerFacade.findAll(pageable);
		List<CustomerResponseVO> customerResponseVOs = new ArrayList<>();
		customerResponseVOs.add(payload_CustomerResponseVO());
		assertEquals(customerResponseVOs, result);
	}

	@Test
	public void findById() throws ParseException {
		when(customerService.findById(ArgumentMatchers.anyInt())).thenReturn(payload_Customer());
		CustomerResponseVO result = customerFacade.findById(1);
		assertEquals(payload_CustomerResponseVO(), result);
	}

	private Customer payload_Customer() throws ParseException {
		final Customer customer = new Customer();
		customer.setId(1);
		customer.setName("test");
		customer.setCpf("778.884.930-84");
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		customer.setBirthDate(f.parse("01/01/1990"));
		return customer;
	}

	private CustomerResponseVO payload_CustomerResponseVO() throws ParseException {
		final CustomerResponseVO customerResponseVO = new CustomerResponseVO();
		customerResponseVO.setId(1);
		customerResponseVO.setName("test");
		customerResponseVO.setCpf("778.884.930-84");
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		customerResponseVO.setBirthDate(f.parse("01/01/1990"));
		return customerResponseVO;
	}

}
