package fr.mssd.frozenmeat.repository;

import org.springframework.data.repository.CrudRepository;

import fr.mssd.frozenmeat.model.Product;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProductRepository extends CrudRepository<Product, Integer> {

	Product getByCode(String code);
	

}
