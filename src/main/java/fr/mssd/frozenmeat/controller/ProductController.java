package fr.mssd.frozenmeat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import fr.mssd.frozenmeat.model.Product;
import fr.mssd.frozenmeat.openfoodfacts.OFFProductData;
import fr.mssd.frozenmeat.repository.ProductRepository;

@CrossOrigin
@Controller	
@RequestMapping(path="/product") 
public class ProductController extends AbstractController<Product, ProductRepository>{
	
	protected static final String OFF_REST_API_URL = "https://fr.openfoodfacts.org/api/v0/produit/{1}.json";
	
	@Autowired
	protected RestTemplate restTemplate;
	
	@GetMapping(path="/code/{code}")
	public @ResponseBody Product addByCode(@PathVariable("code") String code) {
		
		Product p = repository.getByCode(code);
		
		if(p == null) {
			OFFProductData data = restTemplate.getForObject(OFF_REST_API_URL.replace("{1}", code), OFFProductData.class);
			p = new Product();
			p.setCode(data.getCode());
			if(data.getProduct() != null) {
				p.setBrands(data.getProduct().getBrands());
				p.setImageFrontThumbUrl(data.getProduct().getImage_front_thumb_url());
				p.setImageUrl(data.getProduct().getImage_url());
				p.setProductName(data.getProduct().getProduct_name());
			}
			p = repository.save(p);
		}
		return p;
	}
}
