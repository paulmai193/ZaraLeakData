package logia.zara.db;

import java.util.Arrays;

import logia.redis.data.HashRedisClass;
import logia.zara.model.SaleProductData;

/**
 * The Class Data.
 *
 * @author Paul Mai
 */
public class Data extends HashRedisClass {

	/** The Constant KEY. */
	public static final String	KEY	= "product:<ref>";

	/** The ref. */
	private String				ref;

	/**
	 * Instantiates a new data.
	 */
	public Data() {
	}

	/**
	 * Instantiates a new data.
	 *
	 * @param __saleProductData the sale product data
	 */
	public Data(SaleProductData __saleProductData) {
		this(__saleProductData.getRef());
		this.setValue(__saleProductData.getProductData().toMap());
	}

	/**
	 * Instantiates a new data.
	 *
	 * @param __ref the ref
	 */
	public Data(String __ref) {
		this.setKey(Data.KEY.replace("<ref>", __ref));
		this.ref = __ref;
	}

	/**
	 * To sale product data.
	 *
	 * @return the sale product data
	 */
	public SaleProductData toSaleProductData() {
		SaleProductData _saleProductData = new SaleProductData();
		_saleProductData.setRef(this.ref);
		_saleProductData.getProductData().setLink(this.getValue().get("link"));
		_saleProductData.getProductData()
		.setOnSale(Boolean.getBoolean(this.getValue().get("on_sale")));
		_saleProductData.getProductData().setPhotoUrl(this.getValue().get("product_photo"));
		_saleProductData.getProductData().setProductName(this.getValue().get("product_name"));
		_saleProductData.getProductData().setProductPrice(this.getValue().get("product_price"));
		_saleProductData.getProductData().setCurrency(this.getValue().get("currency"));
		String _priceString = this.getValue().get("product_size");
		_priceString = _priceString.substring(1, _priceString.length() - 2);
		_saleProductData.getProductData().setProductSizes(Arrays.asList(_priceString.split(",")));

		return _saleProductData;
	}

}
