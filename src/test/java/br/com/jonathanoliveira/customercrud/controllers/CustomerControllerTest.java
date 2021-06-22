package br.com.jonathanoliveira.customercrud.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.jonathanoliveira.customercrud.data.entities.Customer;
import br.com.jonathanoliveira.customercrud.data.vos.CustomerRequestVO;
import br.com.jonathanoliveira.customercrud.data.vos.CustomerResponseVO;
import br.com.jonathanoliveira.customercrud.facades.CustomerFacade;

public class CustomerControllerTest extends AbstractTest {

	@MockBean
	private CustomerFacade customerFacade;

	@Before
	public void init() {
		setUp();
	}

	@Test
	public void insertCustomer() throws Exception {
		String uri = "/customers";
		String inputJson = super.mapToJson(payload_CustomerRequestVO());
		CustomerResponseVO customerResponseVO = new CustomerResponseVO();
		when(customerFacade.insert(any(CustomerRequestVO.class))).thenReturn(customerResponseVO);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
	}

	@Test
	public void insertCustomer_Unsupported_MediaType() throws Exception {
		String uri = "/customers";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.ALL)
				.content(payload_CustomerRequestVO().toString())).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(415, status);
	}

	@Test
	public void insertCustomer_Bad_Request() throws Exception {
		String uri = "/customers";
		Customer contact = new Customer();
		contact.setName("test");
		String inputJson = super.mapToJson(contact);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	@Test
	public void updateCustomer() throws Exception {
		String uri = "/customers/1";
		CustomerResponseVO customerResponseVO = new CustomerResponseVO();
		when(customerFacade.update(any(Integer.class), any(CustomerRequestVO.class))).thenReturn(customerResponseVO);

		String inputJson = super.mapToJson(payload_CustomerRequestVO());
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void updateCustomer_Method_Not_Allowed() throws Exception {
		String uri = "/customers";

		String inputJson = super.mapToJson(payload_CustomerRequestVO());
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(405, status);
	}

	@Test
	public void updateCustomer_Unsupported_MediaType() throws Exception {
		String uri = "/customers/1";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.ALL)
				.content(payload_CustomerRequestVO().toString())).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(415, status);
	}

	@Test
	public void updateCustomer_Bad_Request() throws Exception {
		String uri = "/customers/1";
		Customer contact = new Customer();
		contact.setName("test");
		String inputJson = super.mapToJson(contact);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	@Test
	public void findById() throws Exception {
		String uri = "/customers/1";

		CustomerResponseVO customerResponseVO = new CustomerResponseVO();
		when(customerFacade.findById(any(Integer.class))).thenReturn(customerResponseVO);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		CustomerResponseVO result = super.mapFromJson(mvcResult.getResponse().getContentAsString(),
				CustomerResponseVO.class);
		assertNotNull(result);
		assertEquals(customerResponseVO, result);

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void findById_Method_Not_Allowed() throws Exception {
		String uri = "/customers/a";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	@Test
	public void findAll() throws Exception {
		String uri = "/customers";

		List<CustomerResponseVO> customerResponseVOs = new ArrayList<>();
		when(customerFacade.findAll()).thenReturn(customerResponseVOs);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		List<CustomerResponseVO> result = super.mapFromJson(mvcResult.getResponse().getContentAsString(),
				new TypeReference<List<CustomerResponseVO>>() {
				});
		assertNotNull(result);
		assertEquals(customerResponseVOs, result);

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	private CustomerRequestVO payload_CustomerRequestVO() throws ParseException {
		final CustomerRequestVO customerRequestVO = new CustomerRequestVO();
		customerRequestVO.setName("Teste");
		customerRequestVO.setCpf("778.884.930-84");
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		customerRequestVO.setBirthDate(f.parse("01/01/1990"));
		return customerRequestVO;
	}

}
