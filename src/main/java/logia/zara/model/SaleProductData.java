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

	/** The link. */
	@JsonKey(key = "link")
	private String      link;

	/** The product data. */
	@JsonKey(key = "product_data")
	private ProductData productData;

	/**
	 * Instantiates a new sale product data.
	 */
	public SaleProductData() {
		this.productData = new ProductData();
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink() {
		return this.link;
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
	 * Sets the link.
	 *
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Sets the product data.
	 *
	 * @param __productData the new product data
	 */
	public void setProductData(ProductData __productData) {
		this.productData = __productData;
	}
}
