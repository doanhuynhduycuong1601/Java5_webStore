package cuong.run.repository;

import java.util.Optional;

import cuong.run.model.Customer;

public interface CustomerRepository extends CommonRepository<Customer, Integer> {
	Optional<Customer> findByEmailEquals(String email);
}
