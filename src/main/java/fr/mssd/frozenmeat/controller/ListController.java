package fr.mssd.frozenmeat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.mssd.frozenmeat.model.Item;
import fr.mssd.frozenmeat.model.List;
import fr.mssd.frozenmeat.model.Product;
import fr.mssd.frozenmeat.repository.ListRepository;
import fr.mssd.frozenmeat.repository.ProductRepository;

@CrossOrigin
@Controller	
@RequestMapping(path="/list") 
public class ListController extends AbstractController<List, ListRepository>{
	
	@Autowired 
	protected ProductRepository productRepository;
	
	/**
	 * Add product to list
	 * nop if product is already in the list
	 * @param listId
	 * @param productId
	 * @param qty
	 * @return
	 */
	@GetMapping(path="/{listId}/{productId}/{qty}")
	public @ResponseBody List addProductToList(@PathVariable("listId") Integer listId, @PathVariable("productId") Integer productId, @PathVariable("qty") Integer qty){
		List list = repository.findById(listId).get();
		Item item = list.getProducts().stream()
				.filter(i -> productId.equals(i.getP().getId()))
				.findAny().orElse(null);
		
		if(item == null) {
			Product product = productRepository.findById(productId).get();
			item = new Item(product, qty);
			list.getProducts().add(item);
		}
		
		return repository.save(list);
	}
	
	/**
	 * Update item qty.
	 * Remove item if qty ==0
	 * @param listId
	 * @param productId
	 * @param qty
	 * @return
	 */
	@GetMapping(path="/update-qty/{listId}/{productId}/{qty}")
	public @ResponseBody List updateQty(@PathVariable("listId") Integer listId, @PathVariable("productId") Integer productId, @PathVariable("qty") Integer qty){
		List list = repository.findById(listId).get();
		Item item = list.getProducts().stream()
			.filter(i -> productId.equals(i.getP().getId()))
			.findAny().orElse(null);
		if(item != null) {
			if(qty == 0) {
				list.getProducts().remove(item);
			}else {
				item.setQty(qty);
			}
		}
		return repository.save(list);
	}
	
	/**
	 * add item qty.
	 * Remove item if resulting qty <= 0
	 * @param listId
	 * @param productId
	 * @param qty
	 * @return
	 */
	@GetMapping(path="/add-qty/{listId}/{productId}/{qty}")
	public @ResponseBody List addQty(@PathVariable("listId") Integer listId, @PathVariable("productId") Integer productId, @PathVariable("qty") Integer qty){
		List list = repository.findById(listId).get();
		Item item = list.getProducts().stream()
			.filter(i -> productId.equals(i.getP().getId()))
			.findAny().orElse(null);
		if(item != null) {
			item.setQty(item.getQty() + qty);
			if(item.getQty() <= 0) {
				list.getProducts().remove(item);
			}
		}else if(qty>0) {
			Product product = productRepository.findById(productId).get();
			item = new Item(product, qty);
			list.getProducts().add(item);
		}else {
			// do nothing : remove a product not in the list
		}
		return repository.save(list);
	}
}
