package fr.mssd.frozenmeat.repository;

import org.springframework.data.repository.CrudRepository;

import fr.mssd.frozenmeat.model.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ListRepository extends CrudRepository<List, Integer> {
	

}
