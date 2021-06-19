package br.com.jonathanoliveira.customercrud.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jonathanoliveira.customercrud.data.vos.CustomerRequestVO;
import br.com.jonathanoliveira.customercrud.data.vos.CustomerResponseVO;
import br.com.jonathanoliveira.customercrud.facades.CustomerFacade;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/customers")
@RequiredArgsConstructor
public class CustomerController {
	private final CustomerFacade clienteFacade;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerResponseVO> insert(@Valid @RequestBody CustomerRequestVO clienteVO) {
		CustomerResponseVO customerResponseVO = this.clienteFacade.insert(clienteVO);
		return new ResponseEntity<>(customerResponseVO, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerResponseVO> update(@PathVariable Integer id,
			@Valid @RequestBody CustomerRequestVO clienteVO) {
		CustomerResponseVO customerResponseVO = this.clienteFacade.update(id, clienteVO);
		return ResponseEntity.ok(customerResponseVO);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustomerResponseVO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<CustomerResponseVO> customerResponseVOList = this.clienteFacade.findAll(pageable);
		return ResponseEntity.ok(customerResponseVOList);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerResponseVO> findById(@PathVariable Integer id) {
		CustomerResponseVO customerResponseVO = this.clienteFacade.findById(id);
		return ResponseEntity.ok(customerResponseVO);
	}

}
