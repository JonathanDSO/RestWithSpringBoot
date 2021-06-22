package br.com.jonathanoliveira.customercrud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.jonathanoliveira.customercrud.data.entities.Customer;
import br.com.jonathanoliveira.customercrud.exceptions.EntityNotFoundException;
import br.com.jonathanoliveira.customercrud.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
	private final CustomerRepository customerRepository;

	public Customer save(Customer customer) {
		Assert.notNull(customer, "customer cannot be null.");
		return this.customerRepository.save(customer);
	}

	public List<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public List<Customer> findAll(Pageable pageable) {
		Assert.notNull(pageable, "pageable cannot be null.");
		return this.customerRepository.findAll(pageable).getContent();
	}

	public Customer findById(Integer id) {
		Assert.notNull(id, "id cannot be null.");
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new EntityNotFoundException("Customer was not found for parameter {id=" + id + "}");
	}

}
