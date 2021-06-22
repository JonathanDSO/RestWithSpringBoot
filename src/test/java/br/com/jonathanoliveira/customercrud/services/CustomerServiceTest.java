package br.com.jonathanoliveira.customercrud.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.jonathanoliveira.customercrud.data.entities.Customer;
import br.com.jonathanoliveira.customercrud.exceptions.EntityNotFoundException;
import br.com.jonathanoliveira.customercrud.repositories.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = CustomerService.class)
public class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Test
	public void save() throws ParseException {
		when(customerRepository.save(any(Customer.class))).thenReturn(payload_Customer());

		Customer result = this.customerService.save(new Customer());
		Assert.assertNotNull(result);
		assertEquals(payload_Customer(), result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void save_null() {
		this.customerService.save(null);
	}

	@Test
	public void findById() throws ParseException {
		final Optional<Customer> customer = Optional.of(payload_Customer());
		when(customerRepository.findById(any(Integer.class))).thenReturn(customer);

		Customer result = customerService.findById(Integer.MAX_VALUE);

		Assert.assertNotNull(result);
		assertEquals(payload_Customer(), result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void findById_idNull() {
		customerService.findById(null);
	}

	@Test(expected = EntityNotFoundException.class)
	public void findById_ContactNotFoundException() {
		final Optional<Customer> customer = Optional.ofNullable(null);
		when(customerRepository.findById(any(Integer.class))).thenReturn(customer);

		customerService.findById(Integer.MAX_VALUE);
	}

	@Test
	public void findAll() throws ParseException {
		final List<Customer> customers = new ArrayList<Customer>();
		customers.add(payload_Customer());
		when(customerRepository.findAll()).thenReturn(customers);

		List<Customer> result = customerService.findAll();

		Assert.assertNotNull(result);
		assertEquals(payload_Customer(), result.get(0));
	}
	
	@Test
	public void findAll_Pageable() throws ParseException {
		Pageable pageable = PageRequest.of(0, 10);
		final List<Customer> customers = new ArrayList<Customer>();
		customers.add(payload_Customer());
		Page<Customer> pageCustomer = new PageImpl<Customer>(customers);
		when(customerRepository.findAll(pageable)).thenReturn(pageCustomer);
		List<Customer> result = customerService.findAll(pageable);

		Assert.assertNotNull(result);
		assertEquals(payload_Customer(), result.get(0));
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

}
