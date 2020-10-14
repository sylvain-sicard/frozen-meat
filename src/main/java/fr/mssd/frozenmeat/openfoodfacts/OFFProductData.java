package fr.mssd.frozenmeat.openfoodfacts;

/**
 * Created by fdgn3063 on 01/02/2017.
 */
public class OFFProductData {

    protected String code;

    protected OFFProduct product;

    public OFFProduct getProduct() {
        return product;
    }

    public void setProduct(OFFProduct product) {
        this.product = product;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	@Override
	public String toString() {
		return "OFFProductData [code=" + code + ", product=" + product + "]";
	}
    
    
}
