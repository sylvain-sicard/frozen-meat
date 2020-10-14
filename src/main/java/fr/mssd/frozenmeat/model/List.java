package fr.mssd.frozenmeat.model;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class List {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	protected Integer id;
	
	protected String name = null;
	
	@ElementCollection
	protected Collection<Item> products = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Item> getProducts() {
		return products;
	}

	public void setProducts(Collection<Item> products) {
		this.products = products;
	}

	

}
