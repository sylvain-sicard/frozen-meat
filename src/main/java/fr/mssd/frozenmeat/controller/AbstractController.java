package fr.mssd.frozenmeat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

 
public class AbstractController<T, R extends CrudRepository<T, Integer>> {
	
	@Autowired 
	protected R repository;

	
	@PostMapping(path="/add") 
	public @ResponseBody T add (@RequestBody T k) {
		k = repository.save(k);
		return k;
	}
	
	@GetMapping(path="/{id}")
	public @ResponseBody T getById(@PathVariable("id") Integer id) {
		return repository.findById(id).get();
	}
	
	@DeleteMapping(path="/{id}")
	public @ResponseBody void deleteById(@PathVariable("id") Integer id) {
		repository.delete(repository.findById(id).get());;
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<T> getAll() {
		return repository.findAll();
	}
	
}
