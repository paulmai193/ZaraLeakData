package logia.zara.model;

import logia.utility.json.annotaion.JsonKey;
import logia.utility.json.annotaion.JsonObject;

/**
 * The Class SaleProductData.
 *
 * @author Paul Mai
 */
@JsonObject
public class SaleProductData {

	/** The product data. */
	@JsonKey(key = "product_data")
	private ProductData	productData;

	/** The link. */
	@JsonKey(key = "ref")
	private String		ref;

	/**
	 * Instantiates a new sale product data.
	 */
	public SaleProductData() {
		this.productData = new ProductData();
	}

	/**
	 * Gets the product data.
	 *
	 * @return the product data
	 */
	public ProductData getProductData() {
		return this.productData;
	}

	/**
	 * Gets the ref.
	 *
	 * @return the ref
	 */
	public String getRef() {
		return this.ref;
	}

	/**
	 * Sets the product data.
	 *
	 * @param __productData the new product data
	 */
	public void setProductData(ProductData __productData) {
		this.productData = __productData;
	}

	/**
	 * Sets the ref.
	 *
	 * @param __ref the new ref
	 */
	public void setRef(String __ref) {
		this.ref = __ref;
	}
}
