package br.com.jonathanoliveira.customercrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jonathanoliveira.customercrud.data.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
