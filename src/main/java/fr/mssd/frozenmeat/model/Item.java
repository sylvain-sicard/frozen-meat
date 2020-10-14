package fr.mssd.frozenmeat.model;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable  
public class Item{
	
	@OneToOne
	protected Product p;
	
	protected Integer qty;
	
	public Item() {}
	
	public Item(Product p, Integer qty) {
		this.p=p;
		this.qty=qty;
	}

	public Product getP() {
		return p;
	}

	public void setP(Product p) {
		this.p = p;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	
	
}
