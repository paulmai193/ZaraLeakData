package logia.zara.model;

import java.util.Arrays;
import java.util.List;

import logia.utility.json.annotaion.JsonKey;
import logia.utility.json.annotaion.JsonObject;

/**
 * The Class ProductData.
 *
 * @author Paul Mai
 */
@JsonObject
public class ProductData {

	/** The on sale. */
	@JsonKey(key = "on_sale")
	private boolean      onSale;

	/** The product name. */
	@JsonKey(key = "product_name")
	private String       productName;

	/** The product price. */
	@JsonKey(key = "product_price")
	private String       productPrice;

	/** The product sizes. */
	private List<String> productSizes;

	/** The photo url. */
	@JsonKey(key = "product_photo")
	private String       photoUrl;

	/**
	 * Gets the photo url.
	 *
	 * @return the photo url
	 */
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public String getProductName() {
		return this.productName;
	}

	/**
	 * Gets the product price.
	 *
	 * @return the product price
	 */
	public String getProductPrice() {
		return this.productPrice;
	}

	/**
	 * Gets the product sizes.
	 *
	 * @return the product sizes
	 */
	public List<String> getProductSizes() {
		return this.productSizes;
	}

	/**
	 * Gets the product sizes to string.
	 *
	 * @return the product sizes to string
	 */
	@JsonKey(key = "product_size")
	public String getProductSizesToString() {
		return Arrays.toString(this.productSizes.toArray());
	}

	/**
	 * Checks if is on sale.
	 *
	 * @return true, if is on sale
	 */
	public boolean isOnSale() {
		return this.onSale;
	}

	/**
	 * Sets the on sale.
	 *
	 * @param __onSale the new on sale
	 */
	public void setOnSale(boolean __onSale) {
		this.onSale = __onSale;
	}

	/**
	 * Sets the photo url.
	 *
	 * @param __photoUrl the new photo url
	 */
	public void setPhotoUrl(String __photoUrl) {
		this.photoUrl = __photoUrl;
	}

	/**
	 * Sets the product name.
	 *
	 * @param __productName the new product name
	 */
	public void setProductName(String __productName) {
		this.productName = __productName;
	}

	/**
	 * Sets the product price.
	 *
	 * @param __productPrice the new product price
	 */
	public void setProductPrice(String __productPrice) {
		this.productPrice = __productPrice;
	}

	/**
	 * Sets the product sizes.
	 *
	 * @param __productSizes the new product sizes
	 */
	public void setProductSizes(List<String> __productSizes) {
		this.productSizes = __productSizes;
	}

}
