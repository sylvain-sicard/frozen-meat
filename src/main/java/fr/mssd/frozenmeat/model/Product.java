package fr.mssd.frozenmeat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	protected Integer id;
	
	protected String code = null;
	
	protected String brands = null;

	protected String productName = null;

	protected String imageFrontThumbUrl = null;

	protected String imageUrl = null;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImageFrontThumbUrl() {
		return imageFrontThumbUrl;
	}

	public void setImageFrontThumbUrl(String imageFrontThumbUrl) {
		this.imageFrontThumbUrl = imageFrontThumbUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
}
