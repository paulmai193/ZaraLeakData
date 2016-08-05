package logia.zara.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import com.google.gson.JsonElement;

import logia.utility.json.JsonUtil;
import logia.utility.json.annotaion.JsonKey;
import logia.utility.json.annotaion.JsonObject;

/**
 * The Class ProductData.
 *
 * @author Paul Mai
 */
@JsonObject
public class ProductData {

	/** The currency. */
	@JsonKey(key="currency")
	private String currency;

	/** The link. */
	@JsonKey(key = "link")
	private String			link;

	/** The on sale. */
	private Boolean			onSale;

	/** The photo url. */
	@JsonKey(key = "product_photo")
	private String			photoUrl;

	/** The product name. */
	@JsonKey(key = "product_name")
	private String			productName;

	/** The product price. */
	@JsonKey(key = "product_price")
	private float			productPrice;

	/** The product sizes. */
	private List<String>	productSizes;

	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public String getCurrency() {
		return this.currency;
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
	 * Gets the on sale.
	 *
	 * @return the on sale
	 */
	@JsonKey(key = "on_sale")
	public String getOnSale() {
		return this.onSale.toString();
	}

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
	public float getProductPrice() {
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
	 * Sets the currency.
	 *
	 * @param __currency the new currency
	 */
	public void setCurrency(String __currency) {
		if (__currency.equals("¥")) {
			// Special, convert ¥ to CNY
			__currency = "CNY";
		}
		this.currency = __currency;
	}

	/**
	 * Sets the link.
	 *
	 * @param __link the new link
	 */
	public void setLink(String __link) {
		this.link = __link;
	}

	/**
	 * Sets the on sale.
	 *
	 * @param __onSale the new on sale
	 */
	public void setOnSale(Boolean __onSale) {
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
	public void setProductPrice(float __productPrice) {
		this.productPrice = __productPrice;
	}

	/**
	 * Sets the product price.
	 *
	 * @param __productPrice the new product price
	 */
	public void setProductPrice(String __productPrice) {
		this.productPrice = Float.parseFloat(__productPrice);
	}

	/**
	 * Sets the product sizes.
	 *
	 * @param __productSizes the new product sizes
	 */
	public void setProductSizes(List<String> __productSizes) {
		this.productSizes = __productSizes;
	}


	/**
	 * To map.
	 *
	 * @return the map
	 */
	public Map<String, String> toMap() {
		com.google.gson.JsonObject _tmpJson = JsonUtil.toJsonObject(this);
		final Map<String, String> _map = new HashMap<>();
		_tmpJson.entrySet().forEach(new Consumer<Entry<String, JsonElement>>() {

			/* (non-Javadoc)
			 * @see java.util.function.Consumer#accept(java.lang.Object)
			 */
			@Override
			public void accept(Entry<String, JsonElement> __t) {
				_map.put(__t.getKey(), __t.getValue().getAsString());
			}
		});
		return _map;
	}
}
