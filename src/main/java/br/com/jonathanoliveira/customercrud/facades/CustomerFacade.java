package br.com.jonathanoliveira.customercrud.facades;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.jonathanoliveira.customercrud.converters.DozerConverter;
import br.com.jonathanoliveira.customercrud.data.entities.Customer;
import br.com.jonathanoliveira.customercrud.data.vos.CustomerRequestVO;
import br.com.jonathanoliveira.customercrud.data.vos.CustomerResponseVO;
import br.com.jonathanoliveira.customercrud.services.CustomerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerFacade {
	private final CustomerService customerService;

	private static final String CUSTOMERVO_CANNOT_BE_NULL = "clienteVO cannot be null";
	private static final String ID_CANNOT_BE_NULL = "clienteVO cannot be null";

	public CustomerResponseVO insert(CustomerRequestVO clienteVO) {
		Assert.notNull(clienteVO, CUSTOMERVO_CANNOT_BE_NULL);
		Customer cliente = DozerConverter.parseObject(clienteVO, Customer.class);
		Customer clienteSaved = customerService.save(cliente);
		return DozerConverter.parseObject(clienteSaved, CustomerResponseVO.class);
	}

	public CustomerResponseVO update(Integer id, CustomerRequestVO clienteVO) {
		Assert.notNull(id, ID_CANNOT_BE_NULL);
		Assert.notNull(clienteVO, CUSTOMERVO_CANNOT_BE_NULL);
		customerService.findById(id);
		Customer cliente = DozerConverter.parseObject(clienteVO, Customer.class);
		cliente.setId(id);
		return DozerConverter.parseObject(customerService.save(cliente), CustomerResponseVO.class);
	}

	public List<CustomerResponseVO> findAll(Pageable pageable) {
		return DozerConverter.parseListObject(customerService.findAll(pageable), CustomerResponseVO.class);
	}
	public List<CustomerResponseVO> findAll() {
		return DozerConverter.parseListObject(customerService.findAll(), CustomerResponseVO.class);
	}

	public CustomerResponseVO findById(Integer id) {
		Assert.notNull(id, ID_CANNOT_BE_NULL);
		return DozerConverter.parseObject(customerService.findById(id), CustomerResponseVO.class);
	}

}
