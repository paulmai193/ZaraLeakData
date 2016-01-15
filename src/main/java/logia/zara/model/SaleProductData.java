package logia.zara.model;

import java.util.List;

/**
 * The Class SaleProductData.
 *
 * @author Paul Mai
 */
public class SaleProductData {

	/** The link. */
	private String       link;

	/** The on sale. */
	private boolean      onSale;

	/** The product name. */
	private String       productName;

	/** The product price. */
	private String       productPrice;

	/** The product sizes. */
	private List<String> productSizes;

	/**
	 * Instantiates a new sale product data.
	 */
	public SaleProductData() {
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
	 * Gets the product name.
	 *
	 * @return the productName
	 */
	public String getProductName() {
		return this.productName;
	}

	/**
	 * Gets the product price.
	 *
	 * @return the productPrice
	 */
	public String getProductPrice() {
		return this.productPrice;
	}

	/**
	 * Gets the product sizes.
	 *
	 * @return the productSizes
	 */
	public List<String> getProductSizes() {
		return this.productSizes;
	}

	/**
	 * Checks if is on sale.
	 *
	 * @return the onSale
	 */
	public boolean isOnSale() {
		return this.onSale;
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
	 * Sets the on sale.
	 *
	 * @param onSale the onSale to set
	 */
	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}

	/**
	 * Sets the product name.
	 *
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Sets the product price.
	 *
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	/**
	 * Sets the product sizes.
	 *
	 * @param productSizes the productSizes to set
	 */
	public void setProductSizes(List<String> productSizes) {
		this.productSizes = productSizes;
	}

}
