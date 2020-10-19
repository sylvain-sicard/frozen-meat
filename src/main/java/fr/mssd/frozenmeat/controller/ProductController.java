package fr.mssd.frozenmeat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	protected static final String OFF_PRODUCT_GET  = "https://fr.openfoodfacts.org/api/v0/produit/{1}.json";
	protected static final String OFF_PRODUCT_EDIT = "https://world.openfoodfacts.org/cgi/product_jqm2.pl?"
			+ "user_id=sylvainsicard&"
			+ "password=1Azertyg&"
			+ "code={1}&"
			+ "product_name={2}&"
			+ "brands={3}";
	
	@Autowired
	protected RestTemplate restTemplate;
	
	@PostMapping(path="/addoff") 
	public @ResponseBody Product add (@RequestBody Product p) {
		String data = restTemplate.getForObject(OFF_PRODUCT_EDIT, String.class, p.getCode(), p.getProductName(), p.getBrands());
		return addByCode(p.getCode());
	}
	
	@GetMapping(path="/code/{code}")
	public @ResponseBody Product addByCode(@PathVariable("code") String code) {
		
		Product p = repository.getByCode(code);
		
		if(p == null) {
			OFFProductData data = restTemplate.getForObject(OFF_PRODUCT_GET, OFFProductData.class, code);
			p = new Product();
			p.setCode(data.getCode());
			if(data.getProduct() != null) {
				p.setBrands(data.getProduct().getBrands());
				p.setImageFrontThumbUrl(data.getProduct().getImage_front_thumb_url());
				p.setImageUrl(data.getProduct().getImage_url());
				p.setProductName(data.getProduct().getProduct_name());
				p = repository.save(p);
			}	
		}
		return p;
	}
	
	@GetMapping(path="/refresh-from-off")
	public @ResponseBody String refreshFromOff() {
		
		Iterable<Product> products = repository.findAll();
		
		for(Product p : products) {
			OFFProductData data = restTemplate.getForObject(OFF_PRODUCT_GET, OFFProductData.class, p.getCode());
			if(data.getProduct() != null) {
				p.setBrands(data.getProduct().getBrands());
				p.setImageFrontThumbUrl(data.getProduct().getImage_front_thumb_url());
				p.setImageUrl(data.getProduct().getImage_url());
				p.setProductName(data.getProduct().getProduct_name());
			}
			p = repository.save(p);
		}
		return "OK";
	}
}
